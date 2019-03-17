/* Posix C implementation of hello world server. */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define SERVER_PORT 8080
#define MSG_MAX_LENGTH 512
#define RESPONSE_TEMPLATE "HTTP/1.1 200 OK\nContent-Type: application/json\nDate: %s\nContent-Length: %zu\n\n%s\n"


void build_response(char* request, size_t request_size, char* response, size_t response_size);
void shutdown_handler(int signo);
void error(const char* message);

int main(void)
{
    int server_socket_fd, client_socket_fd;
    struct sockaddr_in server_address, client_address;
    socklen_t client_address_len;
    char request[MSG_MAX_LENGTH];
    int request_recv_size;
    char response[MSG_MAX_LENGTH] = RESPONSE_TEMPLATE;

    signal(SIGINT, shutdown_handler);

    server_socket_fd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (server_socket_fd == -1) {
        error("Failed to create socket");
    }

    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(SERVER_PORT);
    server_address.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
    if (bind( server_socket_fd, (struct sockaddr *) &server_address, sizeof(server_address)) < 0)
    {
        error("Failed to bind to socket");
    }

    if (listen(server_socket_fd, /*backlog*/ 1) < 0)
    {
        error("Cannot listen incoming connections");
    }

    printf("Listening for incoming connections on port %d ...\n", SERVER_PORT);

    while (1) {
        client_address_len = sizeof(client_address);
        client_socket_fd = accept(
                server_socket_fd,
                (struct sockaddr *) &client_address,
                &client_address_len);
        if (client_socket_fd < 0)
        {
            error("Connection failed");
        }
        printf("Client connection established to %s:%d\n",
                inet_ntoa(client_address.sin_addr),
                ntohs(client_address.sin_port));

        request_recv_size = read(client_socket_fd, request, MSG_MAX_LENGTH);
        if (request_recv_size < 0)
        {
            error("Failed to read request");
        }
        printf("Request received:\n%.*s", request_recv_size, request);
        build_response(request, request_recv_size, response, MSG_MAX_LENGTH);
        puts(response);
        write(client_socket_fd, response, strlen(response));
        close(client_socket_fd);
    }

    return 0;
}

void build_response(char* request, size_t request_size, char* response, size_t response_size)
{
    const size_t response_date_max_size = 64;
    char response_date[response_date_max_size];
    time_t t;
    char name_param[64] = "world";
    const size_t response_body_max_size = 256;
    char response_body[response_body_max_size];
    int param_start, param_end;

    t = time(NULL);
    strftime(response_date, response_date_max_size,  "%a, %d %b %Y %T %Z", localtime(&t));
    for (param_start = 0; param_start < request_size && request[param_start] != '='; param_start++);
    param_start += 1;
    for (param_end = param_start; param_end < request_size && request[param_end] != '&' && request[param_end] != ' '; param_end++);
    if (param_start != param_end)
    {
        memset(name_param, '\0', 64);
        strncpy(name_param, request + param_start,
                param_end-param_start < 63 ? param_end - param_start : 63);
    }
    snprintf(response_body, response_body_max_size,
            "{\"message\":\"Hello %s!\"}", name_param);
    snprintf(response, response_size, RESPONSE_TEMPLATE,
            response_date,
            strlen(response_body) + 1,
            response_body);
}

void shutdown_handler(int signo)
{
    if (signo == SIGINT)
    {
        puts("\nShutting down ...");
        exit(1);
    }
}

void error(const char* message)
{
    perror(message);
    exit(1);
}

