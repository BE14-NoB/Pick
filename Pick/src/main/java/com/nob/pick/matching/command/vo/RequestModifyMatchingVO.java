package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyMatchingVO {
    private int id;
    private String isCompleted;
    private Integer maximumParticipant;
    private Integer levelRange;
    private Integer technologyCategoryId;
}
