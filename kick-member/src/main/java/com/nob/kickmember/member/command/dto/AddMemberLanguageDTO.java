package com.nob.kickmember.member.command.dto;

import lombok.Data;

@Data
public class AddMemberLanguageDTO {
	private Long memberProfilePageId; // 회원 프로필 페이지 ID
	private Long programmingLanguageId; // 추가할 프로그래밍 언어 ID
}