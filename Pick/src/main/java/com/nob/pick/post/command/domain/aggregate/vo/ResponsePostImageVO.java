package com.nob.pick.post.command.domain.aggregate.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostImageVO {
	private int postImageId;
	private String postImagePath;
	private String postImageRenamedName;
	private String postImageIsThumbnail;
}
