package com.nob.pick.matching.query.vo;

import lombok.Getter;

@Getter
public class RequestSearchMatchingVO {
    private Integer technologyCategoryId;
    private Integer maximumParticipantRangeMin;
    private Integer maximumParticipantRangeMax;
    private Integer durationTimeRangeMin;
    private Integer durationTimeRangeMax;
}
