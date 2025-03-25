package com.nob.pick.member.command.dto;

import com.nob.pick.member.query.dto.Status; // Query의 Status enum 재사용
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusCommandDTO {
	@NotNull(message = "상태는 필수입니다.")
	private Status status;
}