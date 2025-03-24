package com.nob.pick.matching.command.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistMatchingEntryDTO {
    private int id;
    private int memberId;
    private int matchingId;
    private String appliedDateAt;
}
