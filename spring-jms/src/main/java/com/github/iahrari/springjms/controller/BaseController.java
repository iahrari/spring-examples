package com.github.iahrari.springjms.controller;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.*;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController {
    protected final String basePath;

    public RouterFunction<ServerResponse> root(String path) {
        return nest(path(path != null ? path: basePath), router());
    }
    
    protected abstract RouterFunction<ServerResponse> router();
}
