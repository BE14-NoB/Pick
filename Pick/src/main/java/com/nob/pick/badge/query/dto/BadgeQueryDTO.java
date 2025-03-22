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
public class BadgeQueryDTO {
	private int id;
	private Integer requirement;
	private int advantage;
	private String description;
	private Boolean isDeleted;
	private int challengeId;
}
