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
	private PostCategory category;
	private String uploadAt;
	private String updateAt;
	private PostStatus status;
	private MemberNicknameDTO member;
}
