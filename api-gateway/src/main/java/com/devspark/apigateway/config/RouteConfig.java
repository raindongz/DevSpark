package com.devspark.apigatway.config;

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
                        .uri("http://localhost:8081")
                )
                .build();
    }
}
