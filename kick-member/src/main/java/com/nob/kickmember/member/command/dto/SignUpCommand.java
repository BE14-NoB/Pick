package com.nob.kickmember.member.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpCommand {
	private String name;
	private Integer age;
	private String ihidnum;
	private String phoneNumber;
	private String email;
	private String password;
	private String nickname;
}