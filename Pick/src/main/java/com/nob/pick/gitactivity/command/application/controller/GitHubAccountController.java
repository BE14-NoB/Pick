package com.nob.pick.gitactivity.command.application.controller;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.gitactivity.command.application.dto.GitHubAccountDTO;
import com.nob.pick.gitactivity.command.application.service.GitHubAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

// 깃에 로그인해서 토큰을 받아오고 저장하는 기능 관리
@Slf4j
@RestController
@RequestMapping("/api/github")
public class GitHubAccountController {
    //    private final JwtUtil jwtUtil;
    private final GitHubAccountService gitHubAccountService;

    @Autowired
    public GitHubAccountController(JwtUtil jwtUtil, GitHubAccountService gitHubAccountService) {
//        this.jwtUtil = jwtUtil;
        this.gitHubAccountService = gitHubAccountService;
    }

    // 자동 연동 콜백 처리 (클라이언트가 GitHub 로그인 후 호출)
    @GetMapping("/callback")
    public ResponseEntity<?> handleOAuthCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.warn("❌ 세션 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션이 존재하지 않습니다.");
        }

        // 세션에 저장되어 있던 유저 아이디와 엑세스 토큰 가져오기
        String githubUserId = (String) session.getAttribute("githubUserId");
        String accessToken = (String) session.getAttribute("githubAccessToken");

        if (githubUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션에 GitHub 로그인 정보가 없습니다.");
        }

        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션에 GitHub 엑세스 토큰이 없습니다.");
        }

        // GITHUB_ACCOUNT 테이블에 저장
        GitHubAccountDTO githubAccountDTO = GitHubAccountDTO.builder()
                .userId(githubUserId)
                .accessToken(accessToken)
                .build();

        try {
            int githubAccountId = gitHubAccountService.registGitHubAccount(githubAccountDTO);
            log.info("githubAccountId : " + githubAccountId);

            // MEMBER 테이블 업데이트
//            String jwt = extractJwt(request);
//            int memberId = jwtUtil.getId(jwt);

            // 🚩 member 테이블의 github_account_id 업데이트 메서드 호출 (githubAccountId 사용)


            // 세션 비우기
            session.removeAttribute("githubUserId");
            session.removeAttribute("githubAccessToken");


            // insert 작업이라 forward만 하면 안되기 때문에 redirect 설정
            response.sendRedirect("/api/github/success");          // 🚩 추후에 마이페이지로 돌아가게 할 예정
            return ResponseEntity.ok("GitHub 연동 완료!");     // redirect 중이라 안해도 되지만 null은 싫어서
        } catch (Exception e) {
            log.error("GitHub 정보 저장 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GitHub 정보 저장 실패");
        }
    }

    @GetMapping("/success")
    public String successPage() {
        return "GitHub 연동이 완료되었습니다!";
    }

    // 깃 인증 정보 삭제 (DB에서 hard delete) - 연동 해제하기 버튼 누르면 호출
    @DeleteMapping("/account")
    public ResponseEntity<?> deleteGitHubAccount(HttpServletRequest request) {
        int gitHubAccountId = getGitHubAccountId(extractJwt(request));

        try {
            gitHubAccountService.deleteGitHubAccount(gitHubAccountId);
            return ResponseEntity.ok("GitHub 인증 데이터가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.error("GitHub 인증 데이터 삭제 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GitHub 인증 데이터 삭제 실패");
        }
    }

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
