package com.nob.pick.post.command.application.dto.member;

import lombok.Getter;

@Getter
public enum Status {
	ACTIVE(0),
	SUSPENDED(1),
	WITHDRAWN(2);

	private final int value;

	Status(int value) {
		this.value = value;
	}

}