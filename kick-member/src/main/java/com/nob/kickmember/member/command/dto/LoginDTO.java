package com.nob.kickmember.member.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	private String email;
	private String password;
	private String status;
}