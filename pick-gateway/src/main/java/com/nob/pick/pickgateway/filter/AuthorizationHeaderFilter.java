package com.nob.pick.pickgateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
    private final Environment env;
    private final JwtParser jwtParser;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
        String secret = env.getProperty("jwt.secret");
        byte[] keyBytes = Base64.getDecoder().decode(secret); // Base64 디코딩 추가
        this.jwtParser = Jwts.parserBuilder()
            .setSigningKey(keyBytes) // 디코딩된 바이트 배열 사용
            .build();
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("Request received - Path: {}", request.getPath());

            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new RuntimeException("No Authorization header"));
            }

            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new RuntimeException("Invalid Authorization header"));
            }

            String jwt = authHeader.substring(7);
            logger.info("JWT Token: {}", jwt);

            try {
                Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
                logger.debug("JWT claims: {}", claims);

                Integer id = claims.get("id", Integer.class);
                String subject = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);

                logger.debug("Extracted id: {}, subject: {}, roles: {}", id, subject, roles);

                // 요청 헤더에 정보 추가
                ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(id)) // String으로 변환하여 헤더에 추가
                    .header("X-User-Roles", String.join(",", roles))
                    .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (JwtException | IllegalArgumentException e) {
                logger.error("JWT parsing error: {}", e.getMessage());
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        };
    }
}