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
        GitHubAccount gitHubAccount = GitHubAccount.builder()
                .userId(gitHubAccountDTO.getUserId())
                .accessToken(gitHubAccountDTO.getAccessToken())
                .build();

        GitHubAccount savedAccount = gitHubAccountRepository.save(gitHubAccount);
        return savedAccount.getId();
    }
}
