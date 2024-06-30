package com.ecommerce_gateway.config.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Bean
    public GlobalFilter logRoute() {
        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

            if (route != null) {
                log.debug("Incoming request to: {}", route.getId());
            }

            return chain.filter(exchange);
        };
    }
}
