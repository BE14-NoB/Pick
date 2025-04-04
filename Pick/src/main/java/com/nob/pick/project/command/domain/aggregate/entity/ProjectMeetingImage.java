package com.nob.pick.project.command.domain.aggregate.entity;

import com.nob.pick.common.config.convertor.BooleanToYNConverter;

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
@Table(name="project_meeting_image")
public class ProjectMeetingImage {  // 프로젝트 회의록 이미지

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "path", nullable = false)
	private String path;

	@Column(name = "is_thumbnail", nullable = false)
	@Convert(converter = BooleanToYNConverter.class)
	private boolean isThumbnail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meeting_id", nullable = false)
	private ProjectMeeting meeting;
}
