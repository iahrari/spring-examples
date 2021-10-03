package com.github.iahrari.springjms.controller;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.*;

@Controller
public class RootController extends BaseController {
    private final List<? extends BaseController> controllers;
    
    public RootController(
        String rootControllerPathPrefix, 
        List<? extends BaseController> controllers
    ) {
        super(rootControllerPathPrefix);
        this.controllers = controllers;
    }

    @Bean
    public RouterFunction<ServerResponse> root(String rootControllerPathPrefix){
        return super.root(rootControllerPathPrefix);
    }

    @Override
    protected RouterFunction<ServerResponse> router(){
        var route = route();
        controllers.forEach(controller -> route.add(controller.root(null)));
        return route.build();
    }
}
