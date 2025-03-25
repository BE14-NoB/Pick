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
public class ProgrammingLanguageInfoDTO {
	private int id;
	private Integer memberLanguageId;
	private String language;
}