package com.nob.pick.project.command.application.vo;

import java.util.List;

import com.nob.pick.project.command.application.dto.ParticipantDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectRoomVO {
	private String name;
	private String durationTime;
	private String startDate;
	private String endDate;
	private int totalParticipants;
	private List<ParticipantDTO> participants;
}
