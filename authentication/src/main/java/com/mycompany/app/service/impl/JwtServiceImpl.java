package com.mycompany.app.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl {
    private long jwtExpiration = 10000000;
    private final String secretKey = "jYe4QrYXFTmEfMbPalZnlDSRyKpu7ryx";

    public String generateToken(UserDetails userDetails, Integer userId) {
        return buildToken( userDetails, userId, jwtExpiration);
    }

    private String buildToken(UserDetails userDetails, Integer userId, long jwtExpiration) {
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("userId", userId);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
