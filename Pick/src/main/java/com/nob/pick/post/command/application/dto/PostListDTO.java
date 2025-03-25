package com.nob.pick.post.command.application.dto;

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
public class PostListDTO {
	private int id;
	private String title;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private MemberNicknameDTO member;
}
