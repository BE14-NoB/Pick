package com.nob.pick.common.util;

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

@Configuration
@EnableWebSecurity
public class WebSecurity {

	private final Environment env;
	private final JwtUtil jwtUtil;
	private final JwtFilter jwtFilter;

	@Autowired
	public WebSecurity(Environment env, JwtUtil jwtUtil, JwtFilter jwtFilter) {
		this.env = env;
		this.jwtUtil = jwtUtil;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(authz ->
				authz
					.requestMatchers(HttpMethod.POST, "/api/members/signup").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/logout").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/edit").authenticated()
					.anyRequest().authenticated()
			)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}