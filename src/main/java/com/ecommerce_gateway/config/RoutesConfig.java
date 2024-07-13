package com.ecommerce_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Value("${product.service.address}")
    private String productUri;

    @Value("${payment.service.address}")
    private String paymentUri;

    @Value("${shopping.cart.service.address}")
    private String shoppingCartUri;

    @Bean
    public RouteLocator custom(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("spring-batch-products", r -> r.path("/ecommerce/inventory/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri(productUri))
                .route("payment", r -> r.path("/ecommerce/payment/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri(paymentUri))
                .route("shopping-cart", r -> r.path("/ecommerce/shopping-cart/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri(shoppingCartUri))
                .build();
    }
}
