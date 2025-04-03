package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.domain.repository.GitHubTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthService { // GitHub OAuth 콜백에서 토큰 자동 저장 처리
    private final JwtUtil jwtUtil;
    private final GitHubTokenRepository gitHubTokenRepository;

    public GitHubOAuthService(JwtUtil jwtUtil, GitHubTokenRepository gitHubTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.gitHubTokenRepository = gitHubTokenRepository;
    }

    public void handleOAuthCallback(String jwt, String accessToken) {
        int userId = jwtUtil.getId(jwt);
        gitHubTokenRepository.save(userId, accessToken);
    }
}