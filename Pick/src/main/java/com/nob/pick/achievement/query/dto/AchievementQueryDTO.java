package com.nob.pick.achievement.query.dto;

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
public class AchievementQueryDTO {
	private int id;
	private int requirement;
	private int exp;
	private Boolean isDeleted;
	private int challengeId;
	private String challengeName;
}
