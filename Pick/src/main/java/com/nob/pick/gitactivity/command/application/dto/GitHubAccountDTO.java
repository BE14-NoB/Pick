package com.nob.pick.gitactivity.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GitHubAccountDTO {
    private String userId;
    private String accessToken;
}
