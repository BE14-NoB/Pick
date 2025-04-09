package com.nob.pick.dailymission.query.dto;

import java.time.LocalDate;

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
public class MemberDailyMissionQueryDTO {
	private int id;
	private boolean isCompleted;
	private LocalDate acceptedDate;
	private int dailyMissionId;
	private int memberId;
}
