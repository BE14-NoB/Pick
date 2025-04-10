package com.nob.pick.gitactivity.command.application.service;

import com.nob.pick.gitactivity.command.application.dto.CommitDTO;
import com.nob.pick.gitactivity.command.application.dto.IssueDTO;
import com.nob.pick.gitactivity.command.application.dto.PullRequestDTO;

import java.util.List;
import java.util.Map;

public interface GitHubActivityService {
    void createGitIssue(int id, String repo, String title, String body);

    void createPullRequest(int id, String repo, String head, String title, String body);

    List<String> getBranches(int id, String repo);

    List<IssueDTO> getIssues(int id, String repo);

    List<CommitDTO> getCommits(int id, String repo);

    List<PullRequestDTO> getPullRequests(int id, String repo);
}
