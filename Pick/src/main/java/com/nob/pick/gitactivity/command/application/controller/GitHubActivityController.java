package com.nob.pick.gitactivity.command.application.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.application.service.GitHubAccountService;
import com.nob.pick.gitactivity.command.application.service.GitHubActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/github")
public class GitHubActivityController {
    private final JwtUtil jwtUtil;
    private final GitHubActivityService gitHubActivityService;

    @Autowired
    public GitHubActivityController(
            JwtUtil jwtUtil
            , GitHubActivityService gitHubActivityService) {
        this.jwtUtil = jwtUtil;
        this.gitHubActivityService = gitHubActivityService;
    }

    // 사용자가 버튼 클릭 → 이슈 생성 API
    @PostMapping("/issue")
    public ResponseEntity<?> createIssue(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String jwt = extractJwt(request);
        String repo = body.get("repo");
        String title = body.get("title");
        String content = body.getOrDefault("body", "");

        int memberId = jwtUtil.getId(jwt);

        // 🚩 memberId를 통해 member를 찾고 해당 데이터의 githubAccountId 값 가져오기
        int gitHubAccountId = 1;        // 우선 임의로 1 저장

        gitHubActivityService.createGitIssue(gitHubAccountId, repo, title, content);
        return ResponseEntity.ok("이슈 생성 완료");
    }

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
