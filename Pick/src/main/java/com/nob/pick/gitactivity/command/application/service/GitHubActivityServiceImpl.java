package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.gitactivity.command.application.dto.CommitDTO;
import com.nob.pick.gitactivity.command.application.dto.IssueDTO;
import com.nob.pick.gitactivity.command.application.dto.LabelDTO;
import com.nob.pick.gitactivity.command.application.dto.PullRequestDTO;
import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import com.nob.pick.gitactivity.command.domain.repository.GitHubAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service("CommandGitHubActivityService")
public class GitHubActivityServiceImpl implements GitHubActivityService {
    private final GitHubAccountRepository gitHubAccountRepository;

    GitHubActivityServiceImpl(GitHubAccountRepository gitHubAccountRepository) {
        this.gitHubAccountRepository = gitHubAccountRepository;
    }

    // 깃 이슈 생성
    @Override
    public void createGitIssue(int id, String repo, String title, String body) {
        // DB에 저장된 githubToken 가져오기
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        String owner = gitHubAccount.getUserId();

        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());
        // 🚩 repo는 실제 레포지터리 이름이어야 해서 달라지면 안되므로 body에서 받아오는게 아니라 따로 설정되어야 함

        Map<String, Object> issue = Map.of("title", title, "body", body);

        client.post()
                .uri("/repos/{owner}/{repo}/issues", owner, repo)
                .bodyValue(issue)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // PR 생성
    @Override
    public void createPullRequest(int id, String repo, String head, String title, String body) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        String owner = gitHubAccount.getUserId();
        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        Map<String, Object> prRequest = Map.of(
                "title", title,
                "body", body,
                "head", head,   // 사용자가 선택한 브랜치
                "base", "main"      // 대상 브랜치는 main으로 고정
        );

        client.post()
                .uri("/repos/{owner}/{repo}/pulls", owner, repo)
                .bodyValue(prRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 브랜치 목록 가져오기
    @Override
    public List<String> getBranches(int id, String repo) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        String owner = gitHubAccount.getUserId();

        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        List<Map<String, Object>> branches = client.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .collectList()
                .block();

        return branches.stream()
                .map(branch -> (String) branch.get("name"))
                .filter(name -> !"main".equals(name))       // main 브랜치 빼고 보여주기
                .collect(Collectors.toList());
    }

    // 이슈 목록 가져오기
    @Override
    public List<IssueDTO> getIssues(int id, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        // 모든 정보 가져오기
        List<Map<String, Object>> raw = client.get()
                .uri("/repos/{owner}/{repo}/issues", account.getUserId(), repo)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .collectList()
                .block();

        // 사용할 정보만 가공해서 넘기기
        return raw.stream().map(issue -> {
            Map<String, Object> user = (Map<String, Object>) issue.get("user");
            List<Map<String, Object>> labels = (List<Map<String, Object>>) issue.get("labels");

            List<LabelDTO> labelList = labels.stream()
                    .map(label -> new LabelDTO((String) label.get("name"), (String) label.get("color")))
                    .toList();

            String milestone = issue.get("milestone") != null
                    ? (String) ((Map<?, ?>) issue.get("milestone")).get("title")
                    : null;

            return new IssueDTO(
                    (int) issue.get("number"),
                    (String) issue.get("title"),
                    labelList,
                    milestone,
                    (String) user.get("login"),
                    (String) user.get("avatar_url"),
                    (String) issue.get("state")
            );
        }).toList();
    }

    // 커밋 목록 가져오기
    @Override
    public List<CommitDTO> getCommits(int id, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        List<Map<String, Object>> raw = client.get()
                .uri("/repos/{owner}/{repo}/commits", account.getUserId(), repo)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .collectList()
                .block();

        return raw.stream().map(commit -> {
            String sha = ((String) commit.get("sha")).substring(0, 7);
            Map<String, Object> commitInfo = (Map<String, Object>) commit.get("commit");
            Map<String, Object> commitAuthor = (Map<String, Object>) commitInfo.get("author");

            Map<String, Object> authorObj = (Map<String, Object>) commit.get("author");
            String author = authorObj != null ? (String) authorObj.get("login") : (String) commitAuthor.get("name");
            String avatar = authorObj != null ? (String) authorObj.get("avatar_url") : null;

            return new CommitDTO(
                    sha,
                    (String) commitInfo.get("message"),
                    author,
                    avatar,
                    (String) commitAuthor.get("date")
            );
        }).toList();
    }

    // PR 목록 가져오기
    @Override
    public List<PullRequestDTO> getPullRequests(int id, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        List<Map<String, Object>> raw = client.get()
                .uri("/repos/{owner}/{repo}/pulls?state=all", account.getUserId(), repo)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .collectList()
                .block();

        return raw.stream().map(pr -> {
            Map<String, Object> user = (Map<String, Object>) pr.get("user");

            return new PullRequestDTO(
                    (int) pr.get("number"),
                    (String) pr.get("title"),
                    (String) user.get("login"),
                    (String) user.get("avatar_url"),
                    (String) pr.get("created_at"),
                    (String) pr.get("state")
            );
        }).toList();
    }

    // 깃 토큰가지고 WebClient 생성
    private WebClient buildGitHubClient(String token) {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + token)
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }

    // DB에 저장된 데이터 찾기
    private GitHubAccount getGitHubAccount(int id) {
        return gitHubAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("저장된 깃 정보 없음"));
    }

}
