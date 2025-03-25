package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProjectMeetingImage {
    private int id;
    private String path;
    private boolean isThumbnail;

    private int meetingId;
}
