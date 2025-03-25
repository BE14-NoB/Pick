package com.nob.pick.post.command.application.dto;

import lombok.Getter;

@Getter
public enum PostCategory {
	DEFAULT(1),
	QNA(2);
	
	private final int value;
	
	PostCategory(int value) {
		this.value = value;
	}
	
}