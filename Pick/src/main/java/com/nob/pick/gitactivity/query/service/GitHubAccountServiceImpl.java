package com.nob.pick.gitactivity.query.service;

import com.nob.pick.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service("QueryGitHubAccountService")
public class GitHubAccountServiceImpl implements GitHubAccountService{
//    private final JwtUtil jwtUtil;
//
//    public GitHubActivityService(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    public void createIssueForUser(String jwt, String owner, String repo, String title, String body) {
//        int userId = jwtUtil.getId(jwt);
//
//        WebClient client = WebClient.builder()
//                .baseUrl("https://api.github.com")
//                .defaultHeader("Authorization", "Bearer " + githubToken)
//                .defaultHeader("Accept", "application/vnd.github+json")
//                .build();
//
//        Map<String, Object> issue = Map.of("title", title, "body", body);
//
//        client.post()
//                .uri("/repos/{owner}/{repo}/issues", owner, repo)
//                .bodyValue(issue)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
}
