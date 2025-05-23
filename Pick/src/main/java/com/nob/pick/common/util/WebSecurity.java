package com.nob.pick.common.util;

import jakarta.servlet.RequestDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final Environment env;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public WebSecurity(Environment env, JwtUtil jwtUtil, JwtFilter jwtFilter, OAuth2AuthorizedClientService authorizedClientService) {
        this.env = env;
        this.jwtUtil = jwtUtil;
        this.jwtFilter = jwtFilter;
        this.authorizedClientService = authorizedClientService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/**").permitAll()


                                // OAuth2
                                .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                                .requestMatchers("/api/github/**").permitAll()
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
                                .requestMatchers("/post/**").permitAll()
                            
                                // 챌린지 관련 도메인 security 설정
                                .requestMatchers("/challenge/**").permitAll()
                                .requestMatchers("/daily-mission/**").permitAll()
                                .requestMatchers("/achievement/**").permitAll()
                                .requestMatchers("/member-achievement/**").permitAll()
                                .requestMatchers("/badge/**").permitAll()

                                // 매칭 관련 도메인 security
                                .requestMatchers("/matching/**").permitAll()
                                .requestMatchers("/matchingEntry/**").permitAll()
                                .requestMatchers("/technologyCategory/**").permitAll()

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))            // 깃 토큰 때문에 임시로 STATELESS -> IF_REQUIRED 수정
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
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
                            OAuth2AuthorizedClient client = authorizedClientService
                                    .loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());

                            String accessToken = client.getAccessToken().getTokenValue();
                            System.out.println("✅ GitHub AccessToken: " + accessToken);

                            // GitHub 유저 ID 가져오기
                            String githubUserId = WebClient.create("https://api.github.com")
                                    .get()
                                    .uri("/user")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .retrieve()
                                    .bodyToMono(Map.class)
                                    .map(user -> String.valueOf(user.get("login")))
                                    .block();

                            // 세션에 저장
                            log.info("✅ OAuth success - 세션 ID: {}", request.getSession().getId());
                            request.getSession().setAttribute("githubUserId", githubUserId);
                            request.getSession().setAttribute("githubAccessToken", accessToken);

                            // 컨트롤러에서 처리하도록 forward (redirect 시 세션이 달라져서 값을 넘길 수가 없음)
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/api/github/callback");
                            dispatcher.forward(request, response);
                        })
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}