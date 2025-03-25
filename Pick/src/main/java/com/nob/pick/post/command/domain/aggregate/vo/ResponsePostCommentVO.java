package com.nob.pick.post.command.domain.aggregate.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostCommentVO {
	private int id;
	private String title;
	private String content;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private int memberId;
	private String memberNickname;
	private List<ResponsePostImageVO> postImageVOList;
	private List<ResponseCommentVO> commentVOList;
}
