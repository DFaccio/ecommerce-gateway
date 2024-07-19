package com.ecommerce_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RoutesConfig {

    @Value("${product.service.address}")
    private String productUri;

    @Value("${payment.service.address}")
    private String paymentUri;

    @Value("${shopping.cart.service.address}")
    private String shoppingCartUri;

    @Value("${users.service.address}")
    private String userUri;

    @Bean
    public RouteLocator custom(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("inventory", r -> r.path("/ecommerce/inventory/**")
                        .and().not(p -> p.path("/ecommerce/inventory/documentation/**", "/ecommerce/inventory/doc/**"))
                        .filters(f -> f.stripPrefix(2))
                        .uri(productUri))
                .route("payment", r -> r.path("/ecommerce/payment/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri(paymentUri))
                .route("shopping-cart", r -> r.path("/ecommerce/shopping-cart/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri(shoppingCartUri))
                .route("users", r -> r.path("/ecommerce/authentication-api/**")
                        .and().not(p -> p.path("/ecommerce/authentication-api/documentation/**", "/ecommerce/authentication-api/doc/**"))
                        .filters(f -> f.stripPrefix(2))
                        .uri(userUri))
                .build();
    }

    @Bean
    public RouteLocator docs(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("inventory-doc-swagger-ui", r -> r.path("/ecommerce/inventory/doc/**")
                        .filters(f -> f.stripPrefix(2)
                                .rewritePath("/doc(?<segment>.*)", "/doc${segment}"))
                        .uri(productUri))
                .route("inventory-doc-swagger-json", r -> r.path("/ecommerce/inventory/documentation/**")
                        .filters(f -> f.stripPrefix(2)
                                .rewritePath("/documentation(?<segment>.*)", "/documentation${segment}"))
                        .uri(productUri))
                .route("authentication-doc-swagger-json", r -> r.path("/ecommerce/authentication-api/documentation/**")
                        .filters(f -> f.stripPrefix(2)
                                .rewritePath("/documentation(?<segment>.*)", "/documentation${segment}"))
                        .uri(userUri))
                .route("authentication-doc-swagger-ui", r -> r.path("/ecommerce/authentication-api/doc/**")
                        .filters(f -> f.stripPrefix(2)
                                .rewritePath("/doc(?<segment>.*)", "/doc${segment}"))
                        .uri(userUri))
                .build();
    }
}
