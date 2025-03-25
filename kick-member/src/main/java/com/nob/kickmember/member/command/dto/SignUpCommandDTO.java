package com.nob.kickmember.member.command.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원 가입 요청 받는 DTO
public class SignUpCommandDTO {

	@Size(max = 30, message = "이름은 30자를 초과할 수 없습니다.")
	private String name;

	@Min(value = 0, message = "나이는 0 이상이여야 합니다.")
	private Integer age;

	@Size(max = 20, message = "주민등록 번호는 20자를 초과할 수 없습니다.")
	@Pattern(regexp = "^\\d{6}-\\d{7}$", message = "주민등록번호 형식이 올바르지 않습니다. (예: 123456-1234567)")
	private String ihidnum;

	@Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다.")
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
	private String phoneNumber;

	@Size(max = 255, message = "이메일은 255자를 초과할 수 없습니다.")
	private String email;

	@Size(min = 8, max = 20, message = "비밀번호는 8자리 이상 20자리 이하입니다.")
	private String password;

	@Size(max = 20, message = "별명은 20자리를 초과할 수 없습니다.")
	private String nickname;

	// 반환 값 제공
	@Getter
	private Integer userGrant;

}