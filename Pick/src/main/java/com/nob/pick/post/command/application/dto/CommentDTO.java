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
	private Long commentId;
	private String commentIsAdopted;
	private String commentUploadAt;
	private String commentUpdateAt;
	private String commentContent;
	private PostStatus commentStatus;
	private Long commentPostId;
	private Long commentRootCommentId;	// nullable
	private MemberNicknameDTO commentMember;
}
