package com.nob.pick.matching.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchMatchingDTO {
    private Integer technologyCategoryId;
    private Integer maximum_participant;
    private Integer duration_time;
}
