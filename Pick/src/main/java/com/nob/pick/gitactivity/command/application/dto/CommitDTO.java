package com.nob.pick.gitactivity.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommitDTO {
    private String sha;
    private String message;
    private String author;
    private String avatarUrl;
    private String date;
}
