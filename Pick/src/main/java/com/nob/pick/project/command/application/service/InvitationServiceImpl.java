package com.nob.pick.project.command.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.nob.pick.project.command.application.vo.RequestInviteEmailVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("CommandInvitationService")
public class InvitationServiceImpl implements InvitationService {

	private final JavaMailSender mailSender;

	@Autowired
	public InvitationServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendProjectInviteEmail(RequestInviteEmailVO requestVO) {
		MimeMessage message = mailSender.createMimeMessage();

		String subject = String.format("[PICK] '%s' 프로젝트 방 초대장이 도착했어요!", requestVO.getProjectName());
		String joinUrl = "프론트엔드 실제 입장 URL이 들어갈 곳";

		String body = """
			<div style='font-family: Arial, sans-serif; line-height: 1.6'>
			            <h2>🚀 프로젝트 초대장이 도착했습니다!</h2>
			            <p><b>%s</b>님이 당신을 프로젝트 <b>'%s'</b>에 초대했어요!</p>
			            <p><b>프로젝트 소개:</b> %s</p>
			            <p>입장 코드는 <b style='color: blue;'>%d</b>입니다.</p>
			            <p>
			                아래 버튼을 눌러 프로젝트 입장 페이지로 이동하세요:<br><br>
			                <a href='%s' style='background: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>
			                입장하러 가기
			                </a>
			            </p>
			</div>
			""".formatted(requestVO.getSenderName(),
						  requestVO.getProjectName(),
			requestVO.getProjectContent() != null ? requestVO.getProjectContent() : "설명 없음",
			requestVO.getSessionCode(), joinUrl);

		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(requestVO.getToEmail());
			helper.setSubject(subject);
			helper.setFrom("jms49526@gmail.com");  // (TODO) 보내는 사람 주소

			helper.setText(body, true); // true: HTML 형식으로 보냄
			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송 실패", e);
		}

	}
}
