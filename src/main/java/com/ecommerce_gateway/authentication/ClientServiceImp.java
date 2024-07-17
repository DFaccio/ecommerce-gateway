package com.ecommerce_gateway.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImp {

    @Autowired
    private ClientServiceInterface clientServiceInterface;

    public boolean verifyClientToken(String token) {
        clientServiceInterface.validateToken(token);

        return true;
    }
}
