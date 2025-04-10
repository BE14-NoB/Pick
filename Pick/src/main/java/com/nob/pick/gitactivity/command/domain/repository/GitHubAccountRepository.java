package com.nob.pick.gitactivity.command.domain.repository;

import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GitHubAccountRepository extends JpaRepository<GitHubAccount, Integer> {
    Optional<GitHubAccount> findByUserId(String userId);
}
