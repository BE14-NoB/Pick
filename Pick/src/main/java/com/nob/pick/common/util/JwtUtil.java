package com.nob.pick.common.util;

import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final MemberRepository memberRepository;

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long expiration;

	private byte[] getSigningKey() {
		return Base64.getDecoder().decode(secretKey);
	}

	public String createToken(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
		String role = member.getUserGrant() == 1 ? "ADMIN" : "MEMBER";

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("roles", Collections.singletonList(role));
		Date now = new Date();
		Date validity = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, getSigningKey()) // Base64 디코딩된 키 사용
			.compact();
	}

	public String getEmail(String token) {
		validateTokenFormat(token);
		return Jwts.parser()
			.setSigningKey(getSigningKey())
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public List<String> getRoles(String token) {
		validateTokenFormat(token);
		return Jwts.parser()
			.setSigningKey(getSigningKey())
			.parseClaimsJws(token)
			.getBody()
			.get("roles", List.class);
	}

	public boolean validateToken(String token) {
		try {
			validateTokenFormat(token);
			Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isTokenExpired(String token) {
		try {
			validateTokenFormat(token);
			Claims claims = Jwts.parser()
				.setSigningKey(getSigningKey())
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