# "Hello World" in 90 Languages

## Challenge
Choose a language and write a Programm that listens on port `8080` and answers the a specific `HTTP` request with a speciifed response.

### Test #1

**Request:**

```
POST /say-hello?name=Adam HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Date: ${timeOfResponse}
Content-Length: ${realContentLength}

{"message":"Hello Adam!"}
```

### Test #2

**Request:**

```
POST /say-hello?name=Eva HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Date: ${timeOfResponse}
Content-Length: ${realContentLength}

{"message":"Hello Eva!"}
```
