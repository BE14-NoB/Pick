package com.nob.pick.project.command.application.dto;

import java.util.List;

import com.nob.pick.project.query.aggregate.ProjectMeetingImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
