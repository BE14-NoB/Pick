package com.nob.pick.post.command.application.dto;

import lombok.Getter;

@Getter
public enum PostStatus {
	DEFAULT(0),
	DELETED(1),
	BLINDED(2);
	
	private final int value;
	
	PostStatus(int value) {
		this.value = value;
	}
	
}