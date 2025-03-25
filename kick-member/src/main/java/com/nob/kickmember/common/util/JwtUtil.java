package com.nob.kickmember.common.util;

import com.nob.kickmember.member.command.entity.Member;
import com.nob.kickmember.member.command.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
// @RequiredArgsConstructor
public class JwtUtil {

	private final MemberRepository memberRepository;

	private final Key secretKey;
	private final long expiration;

	public JwtUtil(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.expiration}") long expiration,
		MemberRepository memberRepository
	) {
		byte[] keyBytes = Base64.getDecoder().decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
		this.expiration = expiration;
		this.memberRepository = memberRepository;
	}

	public String createToken(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
		String role = member.getUserGrant() == 0 ? "ADMIN" : "MEMBER";

		// ROLE_ 접두사 추가
		String roleWithPrefix = "ROLE_" + role;
		// System.out.println("Role with prefix: " + roleWithPrefix); // 디버깅 로그 추가

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("roles", Collections.singletonList(roleWithPrefix));
		Date now = new Date();
		Date validity = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
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