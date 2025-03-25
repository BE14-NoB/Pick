package com.nob.kickmember.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final TokenBlacklist tokenBlacklist;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		String method = request.getMethod();

		path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;

		boolean shouldNotFilter =
			(method.equals("POST") && path.equals("/api/members/email")) ||
				(method.equals("POST") && path.equals("/api/members/password")) ||
				(method.equals("POST") && path.equals("/api/members/check-email")) ||
				(method.equals("POST") && path.equals("/api/members/check-phone")) ||
				(method.equals("POST") && path.equals("/api/members/signup")) ||
				(method.equals("POST") && path.equals("/api/members/login"));

		System.out.println("Path: " + path + ", Method: " + method + ", ShouldNotFilter: " + shouldNotFilter);
		return shouldNotFilter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {
		String token = resolveToken(request);

		if (token != null) {
			try {
				System.out.println("Processing token: " + token);
				if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token) && !tokenBlacklist.isBlacklisted(token)) {
					String email = jwtUtil.getEmail(token);
					List<String> roles = jwtUtil.getRoles(token);
					System.out.println("Roles from token: " + roles);

					// ROLE_ 접두사 중복 추가 방지
					List<String> authorities = roles.stream()
						.map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
						.collect(Collectors.toList());
					System.out.println("Authorities after mapping: " + authorities);

					// authorities를 직접 설정
					List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

					UserDetails userDetails = new User(
						email,
						"",
						grantedAuthorities
					);

					System.out.println("UserDetails authorities: " + userDetails.getAuthorities());

					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					System.out.println("Authentication set in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
				} else {
					System.out.println("Token validation failed: invalid, expired, or blacklisted");
				}
			} catch (Exception e) {
				System.out.println("Token processing failed: " + e.getMessage());
			}
		} else {
			System.out.println("No token found in request");
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			String token = bearerToken.substring(7).trim();
			if (token.isEmpty()) {
				return null;
			}
			return token;
		}
		return null;
	}
}