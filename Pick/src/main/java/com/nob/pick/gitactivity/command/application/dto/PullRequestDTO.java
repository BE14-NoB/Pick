package com.nob.pick.gitactivity.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PullRequestDTO {
    private int number;
    private String title;
    private String author;
    private String avatarUrl;
    private String createdAt;
    private String state;

    private int reviewerCount;
    private int commentCount;           // 일반 댓글 수
    private int reviewCommentCount;     // 코드 리뷰 댓글 수
}