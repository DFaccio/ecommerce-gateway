package com.ecommerce_gateway.util;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    /*public static final String SECRET = "66e48fcca777ed0975ff8a7f51198db678aea9661298bcd34adace1ecefa2cce";


    public void validateToken(final String token) {
        Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public String extractCustId(String token) {
        return (String) extractAllClaims(token).get("cartId");
    }

    public Integer extractUserId(String token) {
        return (Integer) extractAllClaims(token).get("userId");
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }*/

}
