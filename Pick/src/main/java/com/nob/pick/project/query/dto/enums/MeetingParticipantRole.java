package com.nob.pick.project.query.dto.enums;

import lombok.Getter;

@Getter
public enum MeetingParticipantRole {
	AUTHOR (0, "작성자"),
	PARTICIPANT (1, "참여자");

	private final int NUM;
	private final String DESCRIPTION;

	MeetingParticipantRole(int num, String description) {
		NUM = num;
		DESCRIPTION = description;
	}

	public static MeetingParticipantRole forNum(int num) {
		for(MeetingParticipantRole role : MeetingParticipantRole.values()) {
			if(role.NUM == num) {return role; }
		}
		throw new IllegalArgumentException("Invalid Num: " + num);
	}
}
