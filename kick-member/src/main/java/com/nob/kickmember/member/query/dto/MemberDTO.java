package com.nob.kickmember.member.query.dto;

import com.nob.kickmember.common.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
	private Long id;
	private String name;
	private int age;
	private String ihidnum;
	private String phoneNumber;
	private String email;
	private String password;
	private String nickname;
	private Status status;
	private int regulatedCount;
	private UserGrant userGrant;
}