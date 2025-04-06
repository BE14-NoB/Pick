package com.nob.pick.project.command.domain.aggregate.entity;

import jakarta.persistence.*;
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
@Table(name="member_review", uniqueConstraints = {
		@UniqueConstraint(
				name = "unique_project_reviewer_reviewee",
				columnNames = {"project_room_id", "reviewer_id", "reviewee_id"}
		)
})
public class MemberReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="rate" , nullable = false)
	private int rate;

	@Column(name="content", columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reviewer_id", nullable = false)
	private Participant reviewerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reviewee_id", nullable = false)
	private Participant revieweeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_room_id" , nullable = false)
	private ProjectRoom projectRoom;



}
