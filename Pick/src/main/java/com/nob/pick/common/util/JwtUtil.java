package com.nob.pick.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
	
	private final Key secretKey;

	public JwtUtil(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Base64.getDecoder().decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

    public String getEmail(String token) {
        validateTokenFormat(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> getRoles(String token) {
        validateTokenFormat(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }

    public int getId(String token) {
        validateTokenFormat(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Integer.class);
    }

    public boolean validateToken(String token) {
        try {
            validateTokenFormat(token);
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            validateTokenFormat(token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private void validateTokenFormat(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 비어 있습니다.");
        }
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("잘못된 토큰 형식입니다: " + token);
        }
    }
}