package com.nob.pick.member.command.vo;

import com.nob.pick.member.query.dto.Status;

public class UpdateMemberStatusVO {
	private final Status status;

	public UpdateMemberStatusVO(Status status) {
		if (status == null) {
			throw new IllegalArgumentException("상태는 null일 수 없습니다.");
		}
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public int getStatusValue() {
		return status.getValue();
	}
}