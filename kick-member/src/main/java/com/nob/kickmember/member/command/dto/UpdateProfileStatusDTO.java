package com.nob.kickmember.member.command.dto;

import lombok.Data;

@Data
public class UpdateProfileStatusDTO {
	private int profilePageId; // PK: 수정할 프로필 페이지 ID
	private String introduce; // 자기소개
	private String preferredArea; // 선호분야
	private String gitAddress; // 깃허브 주소
	private String imagePath; // 프로필 사진 경로
}