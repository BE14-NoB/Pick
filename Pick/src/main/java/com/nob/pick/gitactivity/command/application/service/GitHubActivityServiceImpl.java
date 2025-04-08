package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import com.nob.pick.gitactivity.command.domain.repository.GitHubAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Slf4j
@Service("CommandGitHubActivityService")
public class GitHubActivityServiceImpl implements GitHubActivityService{
    private final GitHubAccountRepository gitHubAccountRepository;

    GitHubActivityServiceImpl(GitHubAccountRepository gitHubAccountRepository) {
        this.gitHubAccountRepository = gitHubAccountRepository;
    }

    @Override
    public void createGitIssue(int id, String repo, String title, String body) {
        // DB에 저장된 githubToken 가져오기
        GitHubAccount gitHubAccount = gitHubAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("저장된 깃 정보 없음"));

        String owner = gitHubAccount.getUserId();
        String githubToken = gitHubAccount.getAccessToken();
        // 🚩 repo는 실제 레포지터리 이름이어야 해서 달라지면 안되므로 body에서 받아오는게 아니라 따로 설정되어야 함


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
