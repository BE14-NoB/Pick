package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMatchingEntryVO {
    private int id;
    private String appliedDateAt;
    private String isCanceled;
    private String isAccepted;
    private int memberId;
    private int matchingId;
}
