package io.vertx.example;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
                req.response().end("Hello vertx world!");
        }).listen(8080);
    }

}
