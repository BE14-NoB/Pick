package com.nob.pick.post.command.domain.aggregate.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCommentVO {
	private int commentId;
	private String commentIsAdopted;
	private String commentUploadAt;
	private String commentUpdateAt;
	private String commentContent;
	private int commentStatus;
	private Integer commentRootCommentId;
	private int commentMemberId;
	private String commentMemberNickname;
}
