package com.nob.pick.project.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="member_review")
public class MemberReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="rate" , nullable = false)
	private int rate;

	@Column(name="content")
	private String content;

	@Column(name="reviewer_id" , nullable = false)
	private int reviewerId;

	@Column(name="reviewee_id", nullable = false)
	private int revieweeId;

	@Column(name="project_room_id", nullable = false)
	private int projectRoomId;


}
