package com.nob.pick.matching.query.aggregate;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
public class MatchingEntry {
    private int id;
    private LocalDateTime appliedDateAt;
    private boolean isCanceled;
    private boolean isAccepted;
    private int memberId;
    private int matchingId;
}
