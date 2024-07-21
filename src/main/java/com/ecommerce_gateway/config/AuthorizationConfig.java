package com.ecommerce_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class AuthorizationConfig {

    private static final String[] AUTH_WHITELIST_DOCUMENTATION = {
            "/ecommerce/inventory/documentation/**",
            "/ecommerce/inventory/doc/**",
            "/ecommerce/authentication-api/documentation/**",
            "/ecommerce/authentication-api/doc/**",
            "/ecommerce/payment/documentation/**",
            "/ecommerce/payment/doc/**",
            "/ecommerce/shopping-cart/documentation/**",
            "/ecommerce/shopping-cart/doc/**"
    };

    private static final String[] SERVICES_WHITELIST = {
            "/ecommerce/authentication-api/login",
            "/ecommerce/authentication-api/users/basic"
    };

    @Value("${jwt.public.key.server}")
    private String jwtPublicKeyServer;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, UrlBasedCorsConfigurationSource corsConfigurationSource) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(AUTH_WHITELIST_DOCUMENTATION).permitAll()
                        .pathMatchers(SERVICES_WHITELIST).permitAll()
                        .pathMatchers(HttpMethod.DELETE, "/ecommerce/inventory/**").hasAuthority("SCOPE_ADMIN")
                        .pathMatchers(HttpMethod.POST, "/ecommerce/inventory/**").hasAuthority("SCOPE_ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/ecommerce/inventory/**").hasAuthority("SCOPE_ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public NimbusReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwtPublicKeyServer).build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
