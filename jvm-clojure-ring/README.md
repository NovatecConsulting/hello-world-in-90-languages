# jvm-clojure-ring

Simple HTTP server in Clojure and the ring library.

By default, the server is configured with tight security.  I had to disable
anti-forgery tokens to get it to accept the `POST` requests from the challenge.
I left the rest of the security middleware in place.


## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

