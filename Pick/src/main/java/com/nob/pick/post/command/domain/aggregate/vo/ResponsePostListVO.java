package com.nob.pick.post.command.domain.aggregate.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostListVO {
	private Long id;
	private String title;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private Long memberId;
	private String memberNickname;
}
