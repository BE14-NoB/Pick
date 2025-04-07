package com.nob.pick.gitactivity.command.domain.repository;

import com.nob.pick.gitactivity.command.domain.aggregate.GitHubAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubAccountRepository extends JpaRepository<GitHubAccount, Integer> {
}
