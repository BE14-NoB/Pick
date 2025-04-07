package com.nob.pick.matching.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchMatchingDTO {
    private Integer technologyCategoryId;
    private Integer maximumParticipantRangeMin;
    private Integer maximumParticipantRangeMax;
    private Integer durationTimeRangeMin;
    private Integer durationTimeRangeMax;
}
