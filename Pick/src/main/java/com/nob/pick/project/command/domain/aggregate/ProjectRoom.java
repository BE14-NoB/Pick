package com.nob.pick.project.command.domain.aggregate;

import java.time.LocalDate;

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
@Table(name = "project_room")
public class ProjectRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Builder.Default
	@Column(nullable = false, length = 4)
	private boolean isFinished = false;

	@Builder.Default
	@Column(nullable = false, length = 4)
	private boolean isDeleted = false;

	@Column(nullable = false)
	private String durationTime;

	@Column(nullable = false)
	private int maximumParticipant;

	@Column(unique = true)
	private Integer sessionCode;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private int technologyCategoryId;

	@Column(unique = true)
	private String thumbnailImage;

	@Column(columnDefinition = "TEXT")
	private String introduction;

	@Column(unique = true)
	private String projectUrl;

}
