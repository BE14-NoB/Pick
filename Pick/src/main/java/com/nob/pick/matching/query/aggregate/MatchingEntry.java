package com.nob.pick.matching.query.aggregate;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class MatchingEntry {
    private int id;
    private Date appliedDateAt;
    private boolean isCanceled;
    private int memberId;
    private int matchingId;
}
