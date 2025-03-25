package com.nob.pick.project.query.dto;

import com.nob.pick.project.query.aggregate.ProjectMeetingImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetingDTO {
    private int id;
    private String title;
    private String content;
    private String uploadTime;;
    private String updateTime;
    private int authorId;
    private String authorName;
    private int projectId;

    private List<ProjectMeetingImage> images;  // 이미지 리스트

}
