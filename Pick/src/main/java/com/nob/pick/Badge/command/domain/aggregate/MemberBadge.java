package com.nob.pick.Badge.command.domain.aggregate;

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
import lombok.Setter;

@Entity
@Table(name = "MEMBER_BADGE")
@Getter
@Setter
public class MemberBadge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "acquired_date", nullable = false)
	private String acquiredDate;

	private int level;

	@ManyToOne
	@JoinColumn(name = "badge_id")
	private Badge badge;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
