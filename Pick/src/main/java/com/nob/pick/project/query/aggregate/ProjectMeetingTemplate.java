package com.nob.pick.project.query.aggregate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProjectMeetingTemplate {
	private int id;
	private String name;
	private String description;
	private String content;
	private int type;
	private boolean isDefault;
}
//