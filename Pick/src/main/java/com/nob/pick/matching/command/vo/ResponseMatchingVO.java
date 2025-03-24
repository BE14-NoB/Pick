package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMatchingVO {
    private int id;
    private String createdDateAt;
    private String isCompleted;
    private String isDeleted;
    private int maximumParticipant;
    private int levelRange;
    private int memberId;
    private int technologyCategoryId;
}
