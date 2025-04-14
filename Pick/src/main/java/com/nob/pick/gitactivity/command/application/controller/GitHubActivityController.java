package com.nob.pick.gitactivity.command.application.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.application.dto.CommitDTO;
import com.nob.pick.gitactivity.command.application.dto.IssueDTO;
import com.nob.pick.gitactivity.command.application.dto.PullRequestDTO;
import com.nob.pick.gitactivity.command.application.service.GitHubActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 이슈, PR 생성 등 깃 활동 관련
@Slf4j
@RestController
@RequestMapping("/api/github")
public class GitHubActivityController {
//    private final JwtUtil jwtUtil;
    private final GitHubActivityService gitHubActivityService;

    @Autowired
    public GitHubActivityController(JwtUtil jwtUtil, GitHubActivityService gitHubActivityService) {
//        this.jwtUtil = jwtUtil;
        this.gitHubActivityService = gitHubActivityService;
    }

    // 사용자가 버튼 클릭 → 이슈 생성 API
    @PostMapping("/issue")
    public ResponseEntity<?> createIssue(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String repo = body.get("repo");
        String owner = body.get("owner");
        String title = body.get("title");
        String content = body.getOrDefault("body", "");

        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        gitHubActivityService.createGitIssue(gitHubAccountId, owner, repo, title, content);
        return ResponseEntity.ok("이슈 생성 완료");
    }

    // PR 생성 API (사용자가 프론트에서 선택한 브랜치명 -> main)
    @PostMapping("/pull-request")
    public ResponseEntity<?> createPullRequestAuto(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String repo = body.get("repo");
        String owner = body.get("owner");
        String head = body.get("head");  // 사용자가 선택한 작업 브랜치명
        String title = body.get("title");
        String content = body.getOrDefault("body", "");
//        String base = body.get("base");  // 머지 대상(항상 main으로 Service에서 설정중)

        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        gitHubActivityService.createPullRequest(gitHubAccountId, owner, repo, head, title, content);
        return ResponseEntity.ok("PR 생성 완료");
    }

    // 이슈 목록 조회
    @GetMapping("/issues")
    public ResponseEntity<?> getIssues(@RequestParam String repo, @RequestParam String owner, HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));
        List<IssueDTO> issues = gitHubActivityService.getIssues(gitHubAccountId, owner, repo);
        return ResponseEntity.ok(issues);
    }

    // 커밋 목록 조회
    @GetMapping("/commits")
    public ResponseEntity<?> getCommits(@RequestParam String repo, @RequestParam String owner, HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        List<CommitDTO> commits = gitHubActivityService.getCommits(gitHubAccountId, owner, repo);
        return ResponseEntity.ok(commits);
    }

    // pr 목록 조회
    @GetMapping("/pull-requests")
    public ResponseEntity<?> getPullRequests(@RequestParam String repo, @RequestParam String owner, HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        List<PullRequestDTO> prs = gitHubActivityService.getPullRequests(gitHubAccountId, owner, repo);
        return ResponseEntity.ok(prs);
    }

    // 브랜치 목록 조회 API (PR 생성 시 사용자가 선택할 목록 (figma - "PR 생성 페이지 - 브랜치 선택" 페이지 참조))
    @GetMapping("/branches")
    public ResponseEntity<?> getBranches(@RequestParam String repo, @RequestParam String owner, HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        List<String> branches = gitHubActivityService.getBranches(gitHubAccountId, owner, repo);
        return ResponseEntity.ok(branches);
    }

    // 해당 브랜치의 커밋 목록 조회
    @GetMapping("/branchCommits")
    public ResponseEntity<?> getBranchCommits(@RequestParam String repo,
                                              @RequestParam String owner,
                                              @RequestParam String branchName,
                                              HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        List<CommitDTO> branches = gitHubActivityService.getBranchCommit(gitHubAccountId, owner, repo, branchName);
        return ResponseEntity.ok(branches);
    }

    // 특정

    // 🚩 memberId를 통해 member 데이터를 찾고 해당 데이터의 githubAccountId 값 가져오기
    private int getGitHubAccountId(String jwt) {
//        int memberId = jwtUtil.getId(jwt);

        return 1;       // 임시값
    }

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
