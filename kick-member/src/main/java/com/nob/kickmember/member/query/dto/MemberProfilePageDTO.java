package com.nob.kickmember.member.query.dto;

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
public class MemberProfilePageDTO {
	private int id;
	private int level;
	private String introduce;
	private String preferredArea;
	private Integer score;
	private String imagePath;
	private int memberId;
}