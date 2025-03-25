package com.nob.pick.regulation.command.application.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegulationDTO {
    private int memberId;
    private int count;      // 쿼리에서 조회된 제재 횟수
}
