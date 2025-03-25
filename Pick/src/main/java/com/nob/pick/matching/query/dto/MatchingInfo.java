package com.nob.pick.matching.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MatchingInfo {
    private int matchingId; // 매칭방 id
    private int managerId;  // 방장 id
    private int managerLevel;   // 방장 level
}
