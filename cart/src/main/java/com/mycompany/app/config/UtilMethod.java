package com.mycompany.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class UtilMethod {

    private final String secretKey = "jYe4QrYXFTmEfMbPalZnlDSRyKpu7ryx";

    public Integer extractUserIdFromAuthorizationToken(String authorization) {
        String jwt = authorization.substring(7);
        return extractUserId(jwt);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Integer extractUserId(String token){
        return extractAllClaims(token).get("userId", Integer.class);
    }

    private Key getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
