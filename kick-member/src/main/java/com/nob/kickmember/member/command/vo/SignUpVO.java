package com.nob.kickmember.member.command.vo;

import java.util.Objects;

import lombok.Getter;


@Getter
public class SignUpVO {
	private final String name;
	private final Integer age;
	private final String ihidnum;
	private final String phoneNumber;
	private final String email;
	private final String password;
	private final String nickname;

	public SignUpVO(String name, Integer age, String ihidnum, String phoneNumber, String email, String password, String nickname) {
		this.name = name;
		this.age = age;
		this.ihidnum = ihidnum;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		SignUpVO signUpVO = (SignUpVO)o;
		return Objects.equals(name, signUpVO.name) && Objects.equals(age, signUpVO.age)
			&& Objects.equals(ihidnum, signUpVO.ihidnum) && Objects.equals(phoneNumber,
			signUpVO.phoneNumber) && Objects.equals(email, signUpVO.email) && Objects.equals(password,
			signUpVO.password) && Objects.equals(nickname, signUpVO.nickname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age, ihidnum, phoneNumber, email, password, nickname);
	}
}