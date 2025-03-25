package com.nob.kickmember.member.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROGRAMMING_LANGUAGE")
@Getter
@Setter
@NoArgsConstructor
public class ProgrammingLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "LANGUAGE", nullable = false)
	private String language;

	@Column(name = "IS_DELETED", nullable = false, length = 4)
	private String isDeleted;

	public ProgrammingLanguage(String language) {
		this.language = language;
		this.isDeleted = "N"; // 기본값: 삭제되지 않음
	}
}