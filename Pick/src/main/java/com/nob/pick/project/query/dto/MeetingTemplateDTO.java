package com.nob.pick.project.query.dto;

import com.nob.pick.project.query.dto.enums.TemplateType;

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
public class MeetingTemplateDTO {
	private int id;
	private String name;
	private String description;
	private TemplateType type;
	private String content;
	private boolean isDefault;
}
