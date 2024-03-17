package com.devspark.apigatway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouterValidator {
    public static final List<String> openEndPoints = List.of(
            "/user/create",
            "/user/login"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndPoints
                    .stream()
                    .noneMatch(uri -> request
                            .getURI()
                            .getPath()
                            .contains(uri)
                    );
}
