package com.nob.kickmember.member.command.dto;

import com.nob.kickmember.member.query.dto.Status; // Query의 Status enum 재사용
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusCommandDTO {
	@NotNull(message = "상태는 필수입니다.")
	private Status status;
}