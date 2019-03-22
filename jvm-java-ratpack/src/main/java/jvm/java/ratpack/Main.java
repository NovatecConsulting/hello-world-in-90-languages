package jvm.java.ratpack;

import static ratpack.jackson.Jackson.json;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.time.Instant;

public class Main {
    public static void main (String[] args) throws Exception {
        RatpackServer.start(server -> server
                .serverConfig(ServerConfig.embedded().port(8080))
                .handlers(chain -> chain
                        .post("say-hello",
                                ctx -> ctx
                                        .header("Date", Instant.now())
                                        .render(json(new Message("Hello " + ctx.getRequest().getQueryParams().getOrDefault ("name", "World") + "!")))
                        )
                )
        );
    }

    private static class Message {
        public final String message;

        public Message (String message) {
            this.message = message;
        }
    }
}
