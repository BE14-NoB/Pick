package com.nob.pick.project.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="project_meeting_template")
public class ProjectMeetingTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private int id;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="description", nullable=false)
	private String description;

	@Column(name="content", nullable=false)
	private String content;

	@Column(name="type")
	private int type;

	@Column(name="is_default")
	private boolean isDefault;

}
