package com.github.iahrari.springjms.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import com.github.iahrari.springjms.model.HelloWorldMessage;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.*;

@Component
public class RouterController extends BaseController {
    
    public RouterController(String routerControllerPathPrefix) {
        super(routerControllerPathPrefix);
    }
// /router
    @Override
    protected RouterFunction<ServerResponse> router() {
        return route(GET(""), this::rootGet)
                .andRoute(POST("/"), this::rootPost)
                .andRoute(GET("/hello"), this::helloGet)
                .andRoute(GET("/hello-world"), this::helloWorldGet);
    }

    private ServerResponse rootGet(ServerRequest request) {
        return ServerResponse.ok().body(
                    HelloWorldMessage.builder()
                        .message("Root Get!")
                        .build()
                );
    }

    private ServerResponse rootPost(ServerRequest request) {
        return ServerResponse.ok().body(
                HelloWorldMessage.builder()
                    .message("Hello World post!")
                    .build()
            );
    }

    private ServerResponse helloGet(ServerRequest request) {
        return ServerResponse.ok().body(
            HelloWorldMessage.builder()
                    .message("Hello!")
                    .build()
            );
    }

    private ServerResponse helloWorldGet(ServerRequest request) {
        return ServerResponse.ok().body(
            HelloWorldMessage.builder()
                    .message("Hello world get!")
                    .build()
            );
    }
}