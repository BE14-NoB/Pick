package com.nob.kickmember.common.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Status {
	ACTIVE(0,true, null),
	SUSPENDED(1, false, "정지된 회원입니다."),
	WITHDRAWN(2, false, "탈퇴된 회원입니다.");

	private final int value;
	private final boolean canLogin;
	private final String errorMessage;

	Status(int value, boolean canLogin, String errorMessage) {
		this.value = value;
		this.canLogin = canLogin;
		this.errorMessage = errorMessage;
	}


	public static Status fromValue(int value) {
		return Arrays.stream(Status.values())
			.filter(status -> status.getValue() == value)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("유효하지 않은 상태 값입니다: " + value));
	}

}