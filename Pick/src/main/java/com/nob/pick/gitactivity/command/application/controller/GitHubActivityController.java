package com.nob.pick.gitactivity.command.application.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.application.service.GitHubActivityService;
import com.nob.pick.gitactivity.command.application.service.GitHubOAuthService;
import com.nob.pick.gitactivity.command.domain.repository.GitHubTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubActivityController {
    private final GitHubTokenRepository githubTokenRepository;
    private final JwtUtil jwtUtil;
    private final GitHubActivityService gitHubActivityService;
    private final GitHubOAuthService oAuthService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public GitHubActivityController(GitHubTokenRepository githubTokenRepository
                                    , JwtUtil jwtUtil
                                    , GitHubActivityService gitHubActivityService
                                    , GitHubOAuthService oAuthService
                                    , OAuth2AuthorizedClientService authorizedClientService) {
        this.githubTokenRepository = githubTokenRepository;
        this.jwtUtil = jwtUtil;
        this.gitHubActivityService = gitHubActivityService;
        this.oAuthService = oAuthService;
        this.authorizedClientService = authorizedClientService;
    }

    // ✅ 자동 연동 콜백 처리 (클라이언트가 GitHub 로그인 후 호출)
    @GetMapping("/callback")
    public ResponseEntity<?> handleOAuthCallback(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof OAuth2AuthenticationToken oauthToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth 인증 정보 없음");
        }

        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("GitHub AuthorizedClient 없음");
        }

        String accessToken = client.getAccessToken().getTokenValue();

        // 🔐 여기에 JWT 파싱해서 userId 매핑 후 token 저장
        String jwt = extractJwt(request); // Authorization 헤더에서 JWT 추출
        int userId = jwtUtil.getId(jwt);

        githubTokenRepository.save(userId, accessToken);
        return ResponseEntity.ok("GitHub 연동 완료!");
    }



    // ✅ 사용자가 버튼 클릭 → 이슈 생성 API
    @PostMapping("/issue")
    public ResponseEntity<?> createIssue(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String jwt = extractJwt(request);
        String owner = body.get("owner");
        String repo = body.get("repo");
        String title = body.get("title");
        String content = body.getOrDefault("body", "");
        gitHubActivityService.createIssueForUser(jwt, owner, repo, title, content);
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
