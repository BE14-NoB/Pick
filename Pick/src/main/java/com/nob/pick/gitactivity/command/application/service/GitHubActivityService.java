package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.domain.repository.GitHubTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GitHubActivityService {
    private final JwtUtil jwtUtil;
    private final GitHubTokenRepository tokenStore;

    public GitHubActivityService(JwtUtil jwtUtil, GitHubTokenRepository tokenStore) {
        this.jwtUtil = jwtUtil;
        this.tokenStore = tokenStore;
    }

    public void createIssueForUser(String jwt, String owner, String repo, String title, String body) {
        int userId = jwtUtil.getId(jwt);
        String githubToken = tokenStore.get(1);

        WebClient client = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();

        Map<String, Object> issue = Map.of("title", title, "body", body);

        client.post()
                .uri("/repos/{owner}/{repo}/issues", owner, repo)
                .bodyValue(issue)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
