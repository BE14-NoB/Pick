package com.nob.pick.post.command.domain.aggregate.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostCommentVO {
	private Long id;
	private String title;
	private String content;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private Long memberId;
	private String memberNickname;
	private List<ResponsePostImageVO> postImageVOList;
	private List<ResponseCommentVO> commentVOList;
}
