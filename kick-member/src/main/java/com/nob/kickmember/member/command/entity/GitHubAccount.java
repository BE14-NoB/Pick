package com.nob.kickmember.member.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GITHUB_ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
public class GitHubAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "access_token")
	private String accessToken;

	@OneToOne(mappedBy = "githubAccount")
	private Member member;

}
