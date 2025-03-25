package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MemberReview {
    private int id;
    private int rate;
    private String content;
    private int reviewerId;
    private int revieweeId;
    private int projectRoomId;
}
