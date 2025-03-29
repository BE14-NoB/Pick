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

	// Base64방식으로 인코딩. secret -> 우리가 사용할 비밀 코드. expiration -> 토큰 유효 시간.
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

	// 토큰 생성 부분.
	public String createToken(String email) {

		// 멤버 엔티티로 DB에서 입력한 email의 회원 정보를 들고와 권한 체크.
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
		String role = member.getUserGrant() == 0 ? "ADMIN" : "MEMBER";
		// 회원 테이블 id , ROLE_ 접두사 추가
		Long memberId = member.getId();
		String roleWithPrefix = "ROLE_" + role;

		// System.out.println("Role with prefix: " + roleWithPrefix); // 디버깅 로그 추가

		// setSubject() -> 토큰의 용도를 명시
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("id", memberId);
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

	// 각각 토큰에서 이메을은 헤더부분, id와 역할은 바디부분에서 파싱해서 정보를 가져온다.

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

	public Long getId(String token) {
		validateTokenFormat(token);
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("id", Long.class);
	}


	// 유효한 토큰인 검증
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

	// 토큰의 유효기간 확인
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

	// 토큰 형식 확인. null 빈 토큰인지 확인. jwt 토큰 형식 header.payload.signature 체크
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