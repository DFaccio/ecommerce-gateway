package com.ecommerce_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
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

    @Value("${users.service.address}")
    private String userUri;

    @Bean
    public RouteLocator custom(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("inventory", r -> createRoute(r, "/ecommerce/inventory", productUri))
                .route("payment", r -> createRoute(r, "/ecommerce/payment", paymentUri))
                .route("shopping-cart", r -> createRoute(r, "/ecommerce/shopping-cart", shoppingCartUri))
                .route("users", r -> createRoute(r, "/ecommerce/authentication-api", userUri))
                .build();
    }

    private Buildable<Route> createRoute(PredicateSpec route, String path, String uri) {
        return route.path(path + "/**")
                .and().not(p -> p.path(path + "/documentation/**", path + "/doc/**"))
                .filters(f -> f.stripPrefix(2))
                .uri(uri);
    }

    @Bean
    public RouteLocator docs(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("inventory-doc-swagger-ui", r -> createDocumentationRoute(r, "/ecommerce/inventory/doc/**", "/doc", productUri))
                .route("inventory-doc-swagger-json", r -> createDocumentationRoute(r, "/ecommerce/inventory/documentation/**", "/documentation", productUri))
                .route("authentication-doc-swagger-json", r -> createDocumentationRoute(r, "/ecommerce/authentication-api/documentation/**", "/documentation", userUri))
                .route("authentication-doc-swagger-ui", r -> createDocumentationRoute(r, "/ecommerce/authentication-api/doc/**", "/doc", userUri))
                .route("payment-doc-swagger-json", r -> createDocumentationRoute(r, "/ecommerce/payment/documentation/**", "/documentation", paymentUri))
                .route("payment-doc-swagger-ui", r -> createDocumentationRoute(r, "/ecommerce/payment/doc/**", "/doc", paymentUri))
                .route("cart-doc-swagger-json", r -> createDocumentationRoute(r, "/ecommerce/shopping-cart/documentation/**", "/documentation", shoppingCartUri))
                .route("cart-doc-swagger-ui", r -> createDocumentationRoute(r, "/ecommerce/shopping-cart/doc/**", "/doc", shoppingCartUri))
                .build();
    }

    private Buildable<Route> createDocumentationRoute(PredicateSpec route, String path, String documentationPrefix, String uri) {
        return route.path(path)
                .filters(f -> f.stripPrefix(2)
                        .rewritePath(documentationPrefix + "(?<segment>.*)", documentationPrefix + "${segment}"))
                .uri(uri);
    }
}
