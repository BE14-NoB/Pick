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
    private int technologyCategoryId;
}
