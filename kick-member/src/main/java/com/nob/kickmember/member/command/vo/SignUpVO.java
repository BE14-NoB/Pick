package com.nob.kickmember.member.command.vo;

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

}