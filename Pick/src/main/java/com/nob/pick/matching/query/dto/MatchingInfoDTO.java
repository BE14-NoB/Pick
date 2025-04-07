package com.nob.pick.matching.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MatchingInfoDTO {
    private int memberLevel;   // 신청자 레벨
    List<MatchingInfo> matchingInfoList;
    private Integer technologyCategoryId;
    private Integer maximumParticipantRangeMin;
    private Integer maximumParticipantRangeMax;
    private Integer durationTimeRangeMin;
    private Integer durationTimeRangeMax;
}
