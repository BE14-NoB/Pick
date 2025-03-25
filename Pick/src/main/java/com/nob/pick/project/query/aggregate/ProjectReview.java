package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProjectReview {
    private int id;
    private String content;
    private int reviewerId;

    private int projectRoomId;
}

