package com.nob.pick.gitactivity.command.application.service;

import java.util.List;
import java.util.Map;

public interface GitHubActivityService {
    void createGitIssue(int id, String repo, String title, String body);

    void createPullRequest(int id, String repo, String head, String title, String body);

    List<String> getBranches(int id, String repo);

    List<Map<String, Object>> getIssues(int id, String repo);

    List<Map<String, Object>> getCommits(int id, String repo);

    List<Map<String, Object>> getPullRequests(int id, String repo);
}
