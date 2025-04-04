package com.nob.pick.badge.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeAwardDTO {

	private Long memberId;
	private int achievementId;  // 도전과제ID 받아서 진행도 체크
}
