package com.github.iahrari.springjms.controller;

import com.github.iahrari.springjms.model.HelloWorldMessage;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.*;

@Component
public class SecondController extends BaseController {
    public SecondController(String secondControllerPathPrefix) {
        super(secondControllerPathPrefix);
    }

    @Override
    protected RouterFunction<ServerResponse> router() {
        return route(GET(""), this::sayGoodBye);
    }

    private ServerResponse sayGoodBye(ServerRequest request) {
        return ServerResponse.ok().body(
            HelloWorldMessage.builder().message("Goodbye").build()
        );
    }
}
