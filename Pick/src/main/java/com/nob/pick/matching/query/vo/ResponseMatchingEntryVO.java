package com.nob.pick.matching.query.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseMatchingEntryVO {
    private int id;
    private int memberId;
    private int matchingId;
}
