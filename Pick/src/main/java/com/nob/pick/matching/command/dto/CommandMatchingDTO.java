package com.nob.pick.matching.command.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommandMatchingDTO {
    private int id;
    private String createdDateAt;
    private String isCompleted;
    private String isDeleted;
    private int maximumParticipant;
    private int currentParticipant;
    private int levelRange;
    private int memberId;
    private int technologyCategoryId;
}
