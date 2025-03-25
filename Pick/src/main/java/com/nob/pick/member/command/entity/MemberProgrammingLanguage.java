package com.nob.pick.member.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_PROGRAMMING_LANGUAGE")
@Getter
@Setter
@NoArgsConstructor
public class MemberProgrammingLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PROGRAMMING_LANGUAGE_ID", nullable = false)
	private ProgrammingLanguage programmingLanguage;

	@ManyToOne
	@JoinColumn(name = "MEMBER_PROFILE_PAGE_ID", nullable = false)
	private MemberProfilePage memberProfilePage;

	public MemberProgrammingLanguage(ProgrammingLanguage programmingLanguage, MemberProfilePage memberProfilePage) {
		this.programmingLanguage = programmingLanguage;
		this.memberProfilePage = memberProfilePage;
	}
}