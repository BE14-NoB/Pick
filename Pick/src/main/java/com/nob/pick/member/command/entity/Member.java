package com.nob.pick.member.command.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "AGE", nullable = false)
	private Integer age;

	@Column(name = "IHIDNUM", nullable = false, unique = true)
	private String ihidnum;

	@Column(name = "PHONE_NUMBER", nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "NICKNAME", nullable = false, unique = true)
	private String nickname;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@Column(name = "REPORTED_COUNT", nullable = false)
	private Integer reportedCount;

	@Column(name = "USER_GRANT", nullable = false)
	private Integer userGrant; 	// ADMIN(1), MEMBER(0)

	public Member(String name, Integer age, String ihidnum, String phoneNumber, String email, String password, String nickname) {
		this.name = name;
		this.age = age;
		this.ihidnum = ihidnum;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.status = 1; 		 // 가입시 기본 활성화 상태
		this.reportedCount = 0;  // 가입시 신고 횟수 0
		this.userGrant = 2;		 // 가입시 Member 권한 상태
	}
}