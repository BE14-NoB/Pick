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
public class CommentDTO {
	private int commentId;
	private String commentIsAdopted;
	private String commentUploadAt;
	private String commentUpdateAt;
	private String commentContent;
	private int commentStatus;
	private int commentPostId;
	private int commentRootCommentId;
	private MemberNicknameDTO commentMember;
}
