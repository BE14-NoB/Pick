package com.nob.pick.member.command.dto;

import lombok.Data;

@Data
public class UpdateMemberLanguageDTO {
	private Long memberProfilePageId; // 회원 프로필 페이지 ID
	private Long oldProgrammingLanguageId; // 기존 프로그래밍 언어 ID
	private Long newProgrammingLanguageId; // 새로운 프로그래밍 언어 ID
}