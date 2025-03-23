package com.nob.pick.matching.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MatchingEntryDTO {
    private int id;
    private int memberId;
    private int matchingId;
}
