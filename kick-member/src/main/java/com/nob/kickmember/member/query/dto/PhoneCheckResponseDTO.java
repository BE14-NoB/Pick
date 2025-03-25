package com.nob.kickmember.member.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneCheckResponseDTO {
	private boolean isDuplicated;
	private String message;

	public PhoneCheckResponseDTO(boolean isDuplicated, String message) {
		this.isDuplicated = isDuplicated;
		this.message = message;
	}
}