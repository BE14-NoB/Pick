package com.nob.pick.badge.query.dto;

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
public class MemberBadgeQueryDTO {
	private int id;
	private String acquiredDate;
	private int level;
	private int badgeId;
	private int memberId;
}
