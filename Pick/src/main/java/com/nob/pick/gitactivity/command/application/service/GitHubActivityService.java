package com.nob.pick.gitactivity.command.application.service;

import java.util.List;

public interface GitHubActivityService {
    void createGitIssue(int id, String repo, String title, String body);

    void createPullRequest(int id, String repo, String head, String title, String body);

    List<String> getBranches(int id, String repo);
}
