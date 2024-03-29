package com.devspark.apigateway.config;

import com.devspark.apigateway.utils.JWTUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@RefreshScope
@Slf4j
public class AuthenticationFilter implements GatewayFilter {


    private RouterValidator validator;
    private JWTUtil jwtUtil;

    public AuthenticationFilter(RouterValidator validator, JWTUtil jwtUtil) {
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if(validator.isSecured.test(request)){
            if (authMissing(request)){
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            Optional<JWTClaimsSet> claimsSet = Optional.ofNullable(jwtUtil.getClaimSet(token));
            if (claimsSet.isEmpty()){
                log.error("invalid token");
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            exchange.getRequest().mutate().header("user_id", claimsSet.get().getSubject());
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus statusCode){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(statusCode);
        return response.setComplete();
    }
    private boolean authMissing(ServerHttpRequest request){
        return !request.getHeaders().containsKey("Authorization");
    }
}
