package com.nob.kickmember.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	private final Environment env;
	private final JwtUtil jwtUtil;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	public WebSecurity(Environment env, JwtUtil jwtUtil, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.env = env;
		this.jwtUtil = jwtUtil;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // CSRF 비활성화 (토큰 기반 인증에서는 불필요)
			.authorizeHttpRequests(authz ->
				authz
					// 공개 엔드포인트 (인증 없이 접근 가능)
					.requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
					.requestMatchers("/api/members/signup").permitAll()
					.requestMatchers("/api/members/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/email").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/password").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/check-email").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/check-phone").permitAll()

					// 인증 필요 엔드포인트
					.requestMatchers("/api/members/logout").authenticated()
					.requestMatchers("/api/members/edit", "/members/edit").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/members/programming-languages").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PATCH, "/api/members/programming-languages/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/api/members/programming-languages/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/members/programming-languages/member").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/programming-languages/member").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/members/programming-languages/member/**").authenticated()
					.requestMatchers("/api/members/user-info").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/member-Infos").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/member-Info/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/status/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/user-grant/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/profile-page/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/profile-pages/{id}/programming-languages").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/programming-languages").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/edit").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/profile/status/**").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/members/profile/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/{id}").authenticated()

					// 나머지 모든 요청은 인증 필요
					.anyRequest().authenticated()
			)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함 (JWT 기반)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					.authenticationEntryPoint((request, response, authException) -> {
						response.setStatus(HttpStatus.UNAUTHORIZED.value());
						response.setContentType("application/json");
						response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
					})
					.accessDeniedHandler((request, response, accessDeniedException) -> {
						response.setStatus(HttpStatus.FORBIDDEN.value());
						response.setContentType("application/json");
						response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"" + accessDeniedException.getMessage() + "\"}");
					})
			);

		return http.build();
	}
}