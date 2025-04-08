package com.nob.pick.post.command.domain.aggregate.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCommentVO {
	private Long commentId;
	private String commentIsAdopted;
	private String commentUploadAt;
	private String commentUpdateAt;
	private String commentContent;
	private int commentStatus;
	private Long commentRootCommentId;
	private Long commentMemberId;
	private String commentMemberNickname;
}
