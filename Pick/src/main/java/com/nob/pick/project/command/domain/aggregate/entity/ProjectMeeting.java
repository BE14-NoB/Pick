package com.nob.pick.project.command.domain.aggregate.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.nob.pick.common.config.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="project_meeting")
public class ProjectMeeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name="title", nullable=false)
	private String title;

	@Column(name="content" , columnDefinition = "TEXT")
	private String content;

	@Column(name="upload_time", nullable=false)
	private LocalDateTime uploadTime;

	@Column(name="update_time")
	private LocalDateTime updateTime;

	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="author_id", nullable = false)
	private Participant participant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_room_id", nullable = false)
	private ProjectRoom projectRoom;

	public void applyTemplate(String templateContent) {
		this.content = templateContent;
		this.updateTime = LocalDateTime.now();
	}

	public void softDelete() {
		this.isDeleted = true;
		this.updateTime = LocalDateTime.now();
	}

	public void restore() {
		this.isDeleted = false;
		this.updateTime = LocalDateTime.now();
	}
}
