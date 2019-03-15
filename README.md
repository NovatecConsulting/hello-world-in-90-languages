# "Hello World" in 90 Languages

## Challenge
Choose a language and write a Programm that listens on port `8080` and answers the a specific `HTTP` request with a speciifed response.

### Request

```
GET /say-hello HTTP/1.1
Host: localhost:8080
Accept: application/json
```

### Response

```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Date: ${timeOfResponse}
Content-Length: ${realContentLength}

{"message":"Hello World!"}
```
