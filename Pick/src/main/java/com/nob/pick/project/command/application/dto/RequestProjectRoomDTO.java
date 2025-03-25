package com.nob.pick.project.command.application.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestProjectRoomDTO {
	private String name;                        // 방 이름
	private String content;                     // 방 설명
	private int maximumParticipant;             // 방 최대 인원
	private int technologyCategory;             // 카테고리
	private String durationTime;                // 개발 기간

	private List<RequestParticipantDTO> participantList;  // 팀원 목록
}
