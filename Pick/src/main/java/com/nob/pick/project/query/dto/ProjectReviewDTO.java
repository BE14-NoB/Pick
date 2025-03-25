package com.nob.pick.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectReviewDTO {	// 프로젝트 후기 DTO
	private int id;					// 프로젝트 후기 번호
	private int projectRoomId;		// 프로젝트 방 번호
	private String content; 		// 후기 내용
}
