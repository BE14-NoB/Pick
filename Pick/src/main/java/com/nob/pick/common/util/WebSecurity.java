package com.nob.pick.common.config;

import com.nob.pick.common.util.JwtFilter;
import com.nob.pick.common.util.JwtUtil;
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(authz ->
				authz
					.requestMatchers("/**").permitAll()

					// SecurityConfig에서 가져온 설정
					.requestMatchers("/api/members/signup").permitAll()

					.requestMatchers("/actuator/**").permitAll()

					// 테스트용 report 테이블 권한 설정
					.requestMatchers("/query/report/**").permitAll()
					.requestMatchers("/command/report/**").permitAll()


					.requestMatchers("/api/members/edit", "/members/edit").authenticated()
					// WebSecurity 기존 설정
					.requestMatchers(HttpMethod.POST, "/api/members/programming-languages").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PATCH, "/api/members/programming-languages/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/api/members/programming-languages/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/members/programming-languages/member").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/programming-languages/member").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/members/programming-languages/member/**").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/members/email").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/password").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/check-email").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/check-phone").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/members/login").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/members/member-Infos").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/member-Info/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/status/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/user-grant/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/profile-page/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/profile-pages/{id}/programming-languages").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/members/programming-languages").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/edit").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/members/logout").authenticated()
					.requestMatchers(HttpMethod.PATCH, "/api/members/profile/status/**").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/members/profile/**").authenticated()
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