package com.ecommerce_gateway.authentication;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*@Component
@Order(0)*/
public class TokenValidationFilter /*implements GlobalFilter*/ {


    /*private final ClientServiceImp clientServiceImp;

    public TokenValidationFilter(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authorizationHeader.substring(7);

        boolean isValid = validateToken(token);

        if (!isValid) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean validateToken(String token) {
        return clientServiceImp.verifyClientToken(token);
    }*/
}
