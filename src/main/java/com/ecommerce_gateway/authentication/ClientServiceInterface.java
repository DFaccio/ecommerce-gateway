package com.ecommerce_gateway.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("ecommerce-user")
interface ClientServiceInterface {

    @GetMapping("/login/validate")
    JsonNode validateToken(String authorization);
}
