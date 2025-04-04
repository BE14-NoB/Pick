package com.nob.pick.achievement.command.domain.aggregate;

import com.nob.pick.member.command.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_ACHIEVEMENT")
@Getter
@Setter
@NoArgsConstructor
public class MemberAchievement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String achievedDate;

	@Column
	private Integer progress;

	@Column
	private Integer achievedTime;

	@ManyToOne
	@JoinColumn(name = "achievement_id", nullable = false)
	private Achievement achievement;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
}
