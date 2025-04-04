package com.nob.pick.member.query.dto;

import lombok.Getter;

@Getter
public enum UserGrant {
	ADMIN(0),
	MEMBER(1);

	private final int value;

	UserGrant(int value) {
		this.value = value;
	}

}