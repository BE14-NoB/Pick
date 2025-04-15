package com.nob.pick.gitactivity.command.application.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IssueDTO {
    private int number;
    private String title;
    private List<LabelDTO> labels;
    private String milestone;
    private String creator;
    private String avatarUrl;
    private String issueState;
    private String type;
}
