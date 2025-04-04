package com.nob.pick.project.command.domain.aggregate.entity;

import com.nob.pick.common.config.convertor.BooleanToYNConverter;
// import com.nob.pick.member.command.entity.Member;

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
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="participant")
public class Participant {

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="is_manager", nullable = false)
	@Builder.Default
	@Convert(converter = BooleanToYNConverter.class)
	private boolean isManager = false;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="member_id", nullable = false)
	// private Member member;

	@Column(name="member_id", nullable = false)
	private int memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_room_id", nullable = false)
	private ProjectRoom projectRoom;

}
