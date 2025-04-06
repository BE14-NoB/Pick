package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProjectMeetingImage {
    private int id;              // 이미지 아이디
    private String path;         // 이미지 경로
    private boolean isThumbnail; // 이미지 썸네일 여부
    private int orderIndex;      // 이미지 정렬 순서

    private int meetingId;       // 회의록 번호
}


