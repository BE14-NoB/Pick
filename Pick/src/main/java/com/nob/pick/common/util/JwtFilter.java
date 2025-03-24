package com.nob.pick.common.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final TokenBlacklist tokenBlacklist;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		String method = request.getMethod();
		return (path.equals("/api/members/login") && method.equals("POST")) ||
			(path.equals("/api/members/signup") && method.equals("POST"));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {
		String token = resolveToken(request);

		if (token != null) {
			try {
				if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token) && !tokenBlacklist.isBlacklisted(token)) {
					String email = jwtUtil.getEmail(token);
					List<String> roles = jwtUtil.getRoles(token);

					UserDetails userDetails = User.builder()
						.username(email)
						.password("")
						.roles(roles.toArray(new String[0]))
						.build();

					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				// 토큰 검증 실패 시 인증 정보를 설정하지 않고 요청을 계속 처리
				// Spring Security가 이후 인증 여부를 판단
			}
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			String token = bearerToken.substring(7).trim(); // 공백 제거
			if (token.isEmpty()) {
				return null;
			}
			return token;
		}
		return null;
	}
}