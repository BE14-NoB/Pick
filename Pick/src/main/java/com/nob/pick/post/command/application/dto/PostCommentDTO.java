package com.nob.pick.post.command.application.dto;

import java.util.List;

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
public class PostCommentDTO {
	private int id;
	private String title;
	private String content;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private MemberNicknameDTO member;
	private List<PostImageDTO> postImageList;
	private List<CommentDTO> commentList;
}
