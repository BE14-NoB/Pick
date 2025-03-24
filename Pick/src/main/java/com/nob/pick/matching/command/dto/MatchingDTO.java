package com.nob.pick.matching.command.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MatchingDTO {
    private int id;
    private String createdDateAt;
    private String isCompleted;
    private int levelRange;
    private int memberId;
    private int technologyCategoryId;
}
