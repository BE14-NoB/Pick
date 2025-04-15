package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.gitactivity.command.application.dto.*;
import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import com.nob.pick.gitactivity.command.domain.repository.GitHubAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
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
    public void createGitIssue(int id, String owner, String repo, String title, String body) {
        // DB에 저장된 githubToken 가져오기
        GitHubAccount gitHubAccount = getGitHubAccount(id);
//        String owner = gitHubAccount.getUserId();

        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());
        // 🚩 repo는 실제 레포지터리 이름이어야 해서 달라지면 안되므로 body에서 받아오는게 아니라 따로 설정되어야 함

        Map<String, Object> issue = Map.of("title", title, "body", body);

        client.post().uri("/repos/{owner}/{repo}/issues", owner, repo).bodyValue(issue).retrieve().bodyToMono(String.class).block();
    }

    // PR 생성
    @Override
    public void createPullRequest(int id, String owner, String repo, String head, String title, String body) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
//        String owner = gitHubAccount.getUserId();
        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        Map<String, Object> prRequest = Map.of("title", title, "body", body, "head", head,   // 사용자가 선택한 브랜치
                "base", "main"      // 대상 브랜치는 main으로 고정
        );

        client.post().uri("/repos/{owner}/{repo}/pulls", owner, repo).bodyValue(prRequest).retrieve().bodyToMono(String.class).block();
    }

    // 브랜치 목록 가져오기
    @Override
    public List<String> getBranches(int id, String owner, String repo) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        // 모든 브랜치 목록 조회
        List<Map<String, Object>> branches = client.get().uri("/repos/{owner}/{repo}/branches?per_page=100", owner, repo).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
        }).collectList().block();

        List<String> branchNames = branches.stream().map(branch -> (String) branch.get("name")).filter(name -> !"main".equals(name)).collect(Collectors.toList());

        // 닫힌 PR 목록 전체 조회 (페이징 처리)
        Set<String> closedPRBranches = new HashSet<>();
        int page = 1;
        while (true) {
            List<Map<String, Object>> closedPRsPage = client.get().uri("/repos/{owner}/{repo}/pulls?state=closed&per_page=100&page={page}", owner, repo, page).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
            }).collectList().block();

            if (closedPRsPage == null || closedPRsPage.isEmpty()) {
                break; // 더 이상 페이지가 없으면 종료
            }

            closedPRBranches.addAll(closedPRsPage.stream().map(pr -> (Map<String, Object>) pr.get("head")).map(head -> (String) head.get("ref")).collect(Collectors.toSet()));

            page++;
        }

        // 닫힌 PR 브랜치만 제외
        return branchNames.stream().filter(branchName -> !closedPRBranches.contains(branchName)).collect(Collectors.toList());
    }


    // 이슈 목록 가져오기
    @Override
    public List<IssueDTO> getIssues(int id, String owner, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        // 모든 정보 가져오기
        List<Map<String, Object>> allIssues = new ArrayList<>();
        int[] page = {1};

        while (true) {
            List<Map<String, Object>> pageIssues = client.get().uri(uriBuilder -> uriBuilder.path("/repos/{owner}/{repo}/issues").queryParam("state", "all").queryParam("per_page", 100).queryParam("page", page[0]).build(owner, repo)).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
            }).collectList().block();

            if (pageIssues == null || pageIssues.isEmpty()) {
                break;
            }

            allIssues.addAll(pageIssues);

            // 토큰을 위해 200개만
            if (page[0] >= 2) break;
            page[0]++;
        }

        // PR 제외
        List<Map<String, Object>> pureIssues = allIssues.stream().filter(issue -> !issue.containsKey("pull_request")).toList();

        return pureIssues.stream().map(issue -> {
            Map<String, Object> user = (Map<String, Object>) issue.get("user");
            List<Map<String, Object>> labels = (List<Map<String, Object>>) issue.get("labels");

            List<LabelDTO> labelList = labels.stream().map(label -> new LabelDTO((String) label.get("name"), (String) label.get("color"))).toList();

            List<String> labelNames = labels.stream().map(label -> ((String) label.get("name")).toLowerCase()).toList();

            String type = "Other";
            if (labelNames.stream().anyMatch(name -> name.contains("feature") || name.contains("enhancement"))) {
                type = "Feature";
            } else if (labelNames.stream().anyMatch(name -> name.contains("task"))) {
                type = "Task";
            } else if (labelNames.stream().anyMatch(name -> name.contains("bug"))) {
                type = "Bug";
            } else if (labelNames.stream().anyMatch(name -> name.contains("docs") || name.contains("document"))) {
                type = "Docs";
            }

            String milestone = issue.get("milestone") != null ? (String) ((Map<?, ?>) issue.get("milestone")).get("title") : null;

            return new IssueDTO((int) issue.get("number"), (String) issue.get("title"), labelList, milestone, (String) user.get("login"), (String) user.get("avatar_url"), (String) issue.get("state"), type);
        }).toList();
    }

    // 커밋 목록 가져오기
    @Override
    public List<CommitDTO> getCommits(int id, String owner, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        List<Map<String, Object>> raw = client.get().uri("/repos/{owner}/{repo}/commits", account.getUserId(), repo).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
        }).collectList().block();

        return raw.stream().map(commit -> {
            String sha = ((String) commit.get("sha")).substring(0, 7);
            Map<String, Object> commitInfo = (Map<String, Object>) commit.get("commit");
            Map<String, Object> commitAuthor = (Map<String, Object>) commitInfo.get("author");

            Map<String, Object> authorObj = (Map<String, Object>) commit.get("author");
            String author = authorObj != null ? (String) authorObj.get("login") : (String) commitAuthor.get("name");
            String avatar = authorObj != null ? (String) authorObj.get("avatar_url") : null;

            return new CommitDTO(sha, (String) commitInfo.get("message"), author, avatar, (String) commitAuthor.get("date"));
        }).toList();
    }

    // PR 목록 가져오기
    @Override
    public List<PullRequestDTO> getPullRequests(int id, String owner, String repo) {
        GitHubAccount account = getGitHubAccount(id);
        WebClient client = buildGitHubClient(account.getAccessToken());

        List<Map<String, Object>> raw = client.get().uri(uriBuilder -> uriBuilder.path("/repos/{owner}/{repo}/pulls").queryParam("state", "all").build(owner, repo)).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
        }).collectList().block();

        return raw.stream().map(pr -> {
            Map<String, Object> user = (Map<String, Object>) pr.get("user");
            List<Map<String, Object>> reviewers = (List<Map<String, Object>>) pr.get("requested_reviewers");

            int reviewerCount = reviewers != null ? reviewers.size() : 0;
            int commentCount = pr.get("comments") != null ? (int) pr.get("comments") : 0;
            int reviewCommentCount = pr.get("review_comments") != null ? (int) pr.get("review_comments") : 0;

            return new PullRequestDTO((int) pr.get("number"), (String) pr.get("title"), (String) user.get("login"), (String) user.get("avatar_url"), (String) pr.get("created_at"), (String) pr.get("state"), reviewerCount, commentCount, reviewCommentCount);
        }).toList();
    }


    @Override
    public List<CommitDTO> getBranchCommit(int id, String owner, String repo, String branchName) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        List<Map<String, Object>> commits = client.get().uri(uriBuilder -> uriBuilder.path("/repos/{owner}/{repo}/commits").queryParam("sha", branchName).build(owner, repo)).retrieve().bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
        }).collectList().block();

        if (commits == null) return List.of();

        return commits.stream().map(commit -> {
            String sha = (String) commit.get("sha");

            Map<String, Object> commitInfo = (Map<String, Object>) commit.get("commit");
            String message = (String) commitInfo.get("message");

            Map<String, Object> commitAuthorInfo = (Map<String, Object>) commitInfo.get("author");
            String date = commitAuthorInfo != null ? (String) commitAuthorInfo.get("date") : null;

            Map<String, Object> authorInfo = (Map<String, Object>) commit.get("author");
            String author = authorInfo != null ? (String) authorInfo.get("login") : "unknown";
            String avatarUrl = authorInfo != null ? (String) authorInfo.get("avatar_url") : null;

            return new CommitDTO(sha, message, author, avatarUrl, date);
        }).toList();
    }

    @Override
    public BranchDiffDTO getBranchDiff(int id, String owner, String repo, String base, String head) {
        GitHubAccount gitHubAccount = getGitHubAccount(id);
        WebClient client = buildGitHubClient(gitHubAccount.getAccessToken());

        Map<String, Object> result = client.get().uri("/repos/{owner}/{repo}/compare/{base}...{head}", owner, repo, base, head).retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        }).block();

        if (result == null) return new BranchDiffDTO();

        List<Map<String, Object>> fileList = (List<Map<String, Object>>) result.get("files");

        List<ChangedFileDTO> files = fileList.stream().map(file -> new ChangedFileDTO((String) file.get("filename"), (String) file.get("status"), (String) file.getOrDefault("patch", ""))).toList();

        int additions = (int) result.getOrDefault("additions", 0);
        int deletions = (int) result.getOrDefault("deletions", 0);

        return new BranchDiffDTO(files, additions, deletions);
    }

    // 깃 토큰가지고 WebClient 생성
    private WebClient buildGitHubClient(String token) {
        return WebClient.builder().baseUrl("https://api.github.com").defaultHeader("Authorization", "Bearer " + token).defaultHeader("Accept", "application/vnd.github+json").build();
    }

    // DB에 저장된 데이터 찾기
    private GitHubAccount getGitHubAccount(int id) {
        return gitHubAccountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("저장된 깃 정보 없음"));
    }

}
