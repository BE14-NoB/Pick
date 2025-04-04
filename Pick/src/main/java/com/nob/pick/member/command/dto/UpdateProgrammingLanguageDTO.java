package com.nob.pick.member.command.dto;

import lombok.Data;

@Data
public class UpdateProgrammingLanguageDTO {
	private Long id; // 수정할 프로그래밍 언어 ID
	private String language; // 수정할 언어 이름
}