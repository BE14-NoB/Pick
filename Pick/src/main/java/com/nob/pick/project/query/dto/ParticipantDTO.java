package com.nob.pick.project.query.dto;

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
public class ParticipantDTO {  // 프로젝트 팀원 DTO
	private int id;                 // 팀원 번호 (PK)
	private boolean isManager;      // 방장 여부
	private int projectRoomId;      // 프로젝트 방 번호 (FK)
	private int memberId;           // 회원 번호 (FK)

	private String memberName;      // 멤버 이름
	private String memberNickname;  // 닉네임
}
