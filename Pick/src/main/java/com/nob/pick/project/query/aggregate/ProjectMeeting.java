package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class ProjectMeeting {
    private int id;
    private String title;
    private String content;
    private LocalDate uploadTime;
    private LocalDate updateTime;

    private int authorId;
    private int projectRoomId;
}
