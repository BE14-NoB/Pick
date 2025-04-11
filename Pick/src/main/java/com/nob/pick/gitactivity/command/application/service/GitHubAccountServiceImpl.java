package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.gitactivity.command.application.dto.GitHubAccountDTO;
import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import com.nob.pick.gitactivity.command.domain.repository.GitHubAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CommandGitHubAccountService")
public class GitHubAccountServiceImpl implements GitHubAccountService {
    private final GitHubAccountRepository gitHubAccountRepository;

    GitHubAccountServiceImpl(GitHubAccountRepository gitHubAccountRepository) {
        this.gitHubAccountRepository = gitHubAccountRepository;
    }

    @Override
    public int registGitHubAccount(GitHubAccountDTO gitHubAccountDTO) {
        GitHubAccount existing = gitHubAccountRepository.findByUserId(gitHubAccountDTO.getUserId()).orElse(null);

        if (existing != null) {
            log.info("기존 GitHub 계정 존재 - accessToken 업데이트");
            existing.updateToken(gitHubAccountDTO.getAccessToken());
            GitHubAccount updated = gitHubAccountRepository.save(existing);
            return updated.getId();
        } else {
            log.info("새 GitHub 계정 정보 저장");
            GitHubAccount newAccount = GitHubAccount.builder()
                    .userId(gitHubAccountDTO.getUserId())
                    .accessToken(gitHubAccountDTO.getAccessToken())
                    .build();
            GitHubAccount saved = gitHubAccountRepository.save(newAccount);
            return saved.getId();
        }
    }

    @Override
    public void deleteGitHubAccount(int gitHubAccountId) {

        // 🚩 실제 git 토큰을 삭제하는 기능이 추가되어야 함

        // DB에서 저장된 데이터 삭제
        gitHubAccountRepository.deleteById(gitHubAccountId);
    }
}
