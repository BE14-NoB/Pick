package com.nob.pick.project.command.application.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestInviteEmailVO {
	private String toEmail;			// 받는 사람 이메일
	private String receiverName;    // 받는 사람 이름
	private String senderName;		// 보낸 사람 이름
	private int sessionCode;		// 입장 코드 
	private String projectName;		// 프로젝트 이름
	private String projectContent;	// 프로젝트 설명
}
