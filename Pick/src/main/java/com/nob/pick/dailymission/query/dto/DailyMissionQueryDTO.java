package com.nob.pick.dailymission.query.dto;

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
public class DailyMissionQueryDTO {
	private int id;
	private String content;
	private int expPoint;
	private Boolean isDeleted;
	private int challengeId;
}
