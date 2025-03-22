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
public class MemberAchievementQueryDTO {
	private int id;
	private String achievedDate;
	private Integer progress;
	private Integer achievedTime;
	private int achievementId;
	private int memberId;
	private String challengeName;
}
