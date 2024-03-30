package com.devspark.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {


    private AuthenticationFilter filter;

    public RouteConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator route(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/user/**")
                        .filters(f -> f.prefixPath("/api").filter(filter))
                        .uri("lb://auth-service")
                )
                .route(r -> r.path("/user-info/**")
                        .filters(f -> f.prefixPath("/api").filter(filter))
                        .uri("lb://user-service"))
                .route(r -> r.path("/match/**")
                        .filters(f -> f.prefixPath("/api").filter(filter))
                        .uri("lb://match-service"))
                .route(r -> r.path("/chat/**")
                        .filters(f -> f.prefixPath("/api").filter(filter))
                        .uri("lb://chat-service"))
                .build();
    }
}
