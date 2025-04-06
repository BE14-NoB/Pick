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
public class MeetingEditMessageDTO {
	// 실시간 메시지 DTO

	private int meetingId;    		// 회의록 ID
	private int participantId;     // 편집자
	private String content;   		// 내용

}
