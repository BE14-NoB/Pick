package com.nob.pick.post.command.domain.aggregate.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostVO {
	private int id;
	private String title;
	private String content;
	private int category;
	private String uploadAt;
	private String updateAt;
	private int status;
	private Long memberId;
	private String nickname;
}
