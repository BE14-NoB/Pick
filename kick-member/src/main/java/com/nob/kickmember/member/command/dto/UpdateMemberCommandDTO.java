package com.nob.kickmember.member.command.dto;

import com.nob.kickmember.common.enums.Status;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class UpdateMemberCommandDTO {
	@Size(max = 30, message = "이름은 30자를 초과할 수 없습니다.")
	private String name;

	@Min(value = 0, message = "나이는 0 이상이어야 합니다.")
	private Integer age;

	@Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다.")
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
	private String phoneNumber;

	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@Size(max = 255, message = "이메일은 255자를 초과할 수 없습니다.")
	private String email;

	@Size(max = 20, message = "닉네임은 20자를 초과할 수 없습니다.")
	private String nickname;

	@Size(min = 8, max = 20, message = "비밀번호는 8자리 이상 20자리 이하입니다.")
	private String password;

	private Status status;
}