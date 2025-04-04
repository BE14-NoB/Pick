package com.nob.pick.project.query.dto;

import com.nob.pick.project.query.dto.enums.MeetingParticipantRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetingParticipantDTO {
	private int id;
	private int meetingId;
	private int participantId;
	private MeetingParticipantRole participantRole;
}
