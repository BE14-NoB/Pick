package com.nob.kickmember.member.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCheckResponseDTO {
	private boolean isDuplicated;
	private String message;

	public EmailCheckResponseDTO(boolean isDuplicated, String message) {
		this.isDuplicated = isDuplicated;
		this.message = message;
	}
}