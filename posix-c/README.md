Let's have a look at how this works at the OS level.  This solution in C uses
the POSIX socket API.  No frameworks, no non-standard libraries.

For the server to be reachable, it has to

* create a TCP/IP socket,
* bind it to an IP address (here localhost) and a port number,
* listen to it for incoming connection requests, and
* accept one of those connections.

On accepting a connection from a client, it creates a new socket, dedicated to
communication with that specific client.

After establishing the connection, the server simply reads the HTTP request,
extracts the first query parameter value (yep, it's a hack job), and returns
the expected response.  After that, it is done and simply closes the socket.

Of course, the server can be extended, e.g.

* keep the connection open for multiple request/response exchanges,
* support simultaneous connections with multiple clients,
* process requests in parallel on multiple threads
* ...


## Build

You need a C compiler.  Using the GNU C compiler, type

```sh
gcc server.c -o server
```

No, there is no build tool.  No huge list of dependencies.  Not even
a Makefile.


## Run

To run, type

```sh
./server
```

