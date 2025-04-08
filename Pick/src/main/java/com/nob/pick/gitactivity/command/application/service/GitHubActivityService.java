package com.nob.pick.gitactivity.command.application.service;

public interface GitHubActivityService {
    void createGitIssue(int id, String repo, String title, String body);
}
