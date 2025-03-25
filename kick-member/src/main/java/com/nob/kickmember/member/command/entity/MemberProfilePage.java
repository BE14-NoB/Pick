package com.nob.kickmember.member.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_PROFILE_PAGE")
@Getter
@Setter
@NoArgsConstructor
public class MemberProfilePage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "EXP", nullable = false)
	private Integer exp;

	@Column(name = "LEVEL", nullable = false)
	private Integer level;

	@Column(name = "INTRODUCE")
	private String introduce;

	@Column(name = "PREFERRED_AREA")
	private String preferredArea;

	@Column(name = "GIT_ADDRESS")
	private String gitAddress;

	@Column(name = "SCORE")
	private String score;

	@Column(name = "IMAGE_PATH")
	private String imagePath;

	@OneToOne
	@JoinColumn(name = "MEMBER_ID", referencedColumnName = "ID", nullable = false)
	private Member member;

	public MemberProfilePage(Member member, Integer exp, Integer level) {
		this.member = member;
		this.exp = exp;
		this.level = level;
	}
}