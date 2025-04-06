package com.nob.pick.project.command.application.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.nob.pick.project.command.application.dto.MeetingEditMessageDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MeetingSocketController {

	/*
		실시간 회의록 편집 메시지 수신

		클라이언트 -> 서버로 보낸 메시지들
		구독 중인 클라이언트들에게 브로드캐스트
	 */
	@MessageMapping("/meeting/{meetingId}/edit")
	@SendTo("/topic/meeting/{meetingId}")
	public MeetingEditMessageDTO handleEdit(
		@DestinationVariable int meetingId,
		MeetingEditMessageDTO message
	){
		log.info("실시간 회의록 편집 도착 - meetingId = {} : message = {}", meetingId, message);

		return message;
	}


}
