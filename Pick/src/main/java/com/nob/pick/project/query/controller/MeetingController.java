package com.nob.pick.project.query.controller;

import com.nob.pick.project.query.aggregate.ProjectMeetingTemplate;
import com.nob.pick.project.query.dto.MeetingDTO;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;
import com.nob.pick.project.query.service.MeetingService;
import com.nob.pick.project.query.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("QueryMeetingController")
@Slf4j
@RequestMapping("/project")
public class MeetingController {

	private final ParticipantService participantService;
	private MeetingService meetingService;

	@Autowired
	public MeetingController(MeetingService meetingService, ParticipantService participantService) {
		this.meetingService = meetingService;
		this.participantService = participantService;
	}
	
	// 프로젝트별 회의록 목록 조회
	@GetMapping("{projectRoomId}/meeting")
	public ResponseEntity<?> getMeetingList(@PathVariable int projectRoomId, @RequestParam int memberId) {
		log.info("projectRoomId : " + projectRoomId);
		log.info("memberId : " + memberId);

		// 팀원 확인
		boolean isParticipant = participantService.isProjectParticipant(projectRoomId, memberId);
		if (!isParticipant) {
			// 팀원이 아닌 경우 권한 없음 처리
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("프로젝트에 참여한 팀원만 조회할 수 있습니다.");
		}

		List<MeetingDTO> meetingList = meetingService.getMeetingListByProjectId(projectRoomId);
		return ResponseEntity.ok(meetingList);
	}
	
	// 프로젝트 회의록 상세 조회
	@GetMapping("{projectRoomId}/meeting/{meetingId}")
	public ResponseEntity<?> getMeetingDetail(@PathVariable int projectRoomId, @PathVariable int meetingId, @RequestParam int memberId) {
		log.info("projectRoomId : " + projectRoomId);
		log.info("memberId : " + memberId);
		log.info("meetingId : " + meetingId);

		// 팀원 확인
		boolean isParticipant = participantService.isProjectParticipant(projectRoomId, memberId);
		if (!isParticipant) {
			// 팀원이 아닌 경우 권한 없음 처리
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("프로젝트에 참여한 팀원만 조회할 수 있습니다.");
		}
		return ResponseEntity.ok(meetingService.getMeetingByMeetingId(meetingId));
	}

	// #TODO 회의록 템플릿 목록 조회
	@GetMapping("meeting/template")
	public ResponseEntity<?> getMeetingTemplateList() {
		log.info("meeting template list");
		List<MeetingTemplateDTO> templateList = meetingService.getMeetingTemplateList();
		return ResponseEntity.ok(templateList);
	}


	// #TODO 회의록 텝플릿 상세 조회
	@GetMapping("meeting/template/{templateId}")
	public ResponseEntity<?> getMeetingTemplateDetail(@PathVariable int templateId) {
		log.info("{}번 회의록 템플릿 상세 조회", templateId);

		MeetingTemplateDTO template = meetingService.getMeetingTemplateByTemplateId(templateId);
		return ResponseEntity.ok(template);
	}


	// #TODO 타입 별 회의록 템플릿 목록 조회
	@GetMapping("meeting/template/{typeNum}")
	public ResponseEntity<?> getMeetingTemplateListByTypeNum(@PathVariable int typeNum) {
		log.info("{} 타입에 해당하는 템플릿 목록 조회 ", typeNum);

		List<MeetingTemplateDTO> templateList = meetingService.getMeetingTemplateListByTypeNum(typeNum);
		return ResponseEntity.ok(templateList);
	}

	// #TODO 회의록 전체 이미지 목록 조회




}
