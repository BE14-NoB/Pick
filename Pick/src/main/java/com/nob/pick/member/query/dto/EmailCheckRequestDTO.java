package com.nob.pick.member.query.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCheckRequestDTO {
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "유효한 이메일 형식이 아닙니다.")
	private String email;
}