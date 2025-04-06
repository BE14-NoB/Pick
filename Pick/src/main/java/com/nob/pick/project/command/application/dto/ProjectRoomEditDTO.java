package com.nob.pick.project.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectRoomEditDTO {		// 프로젝트 정보 수정 - 텍스트 필드용
	private String name;
	private String introduction;
	private String content;
	private String projectUrl;
}
