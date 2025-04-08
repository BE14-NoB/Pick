package com.nob.pick.project.command.application.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.project.command.application.dto.ProjectInviteRequestDTO;
import com.nob.pick.project.command.application.dto.ProjectRoomEditDTO;
import com.nob.pick.project.command.application.service.InvitationService;
import com.nob.pick.project.command.application.service.InvitationServiceImpl;
import com.nob.pick.project.command.application.service.ProjectRoomServiceImpl;
import com.nob.pick.project.command.application.dto.RequestProjectRoomDTO;
import com.nob.pick.project.command.application.vo.RequestInviteEmailVO;
import com.nob.pick.project.command.application.vo.ResponseProjectRoomVO;
import com.nob.pick.service.AuthService;

import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController("CommandProjectRoomController")
@Slf4j
@RequestMapping("/project")
public class ProjectRoomController {
	private final ProjectRoomServiceImpl projectRoomService;
	private final InvitationServiceImpl invitationService;
	private final JwtUtil jwtUtil;
	private final AuthService authService;


	@Autowired
	public ProjectRoomController(ProjectRoomServiceImpl projectRoomService, InvitationServiceImpl invitationService, JwtUtil jwtUtil,
		AuthService authService) {
		this.projectRoomService = projectRoomService;
		this.invitationService = invitationService;
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	/*  (랜덤 매칭 방)
	 * 	매칭 완료 시 방 생성
	 * 	세션 코드 없음.
	 * 	팀원 전체 목록 추가
	 * */
	@PostMapping("/matching")
	public ResponseEntity<?> registMathingProjectRoom(@RequestBody RequestProjectRoomDTO newProjectRoom) {
		// 세션 코드 없음
		log.info("registNonMatchingProjgectRoom - 새로운 프로젝트 이름: {}", newProjectRoom.getName());

		projectRoomService.createMatchingProject(newProjectRoom);
		return ResponseEntity.ok(Map.of("message", "프로젝트 방이 성공적으로 생성되었습니다!"));
	}

	/*
		(자율 매칭 방)
		방장이 프로젝트 방 생성
		회원이 입장 코드로 입장 & 팀원으로 추가됨
	 */
	@PostMapping("/nonMatching")
	public ResponseEntity<?> registNonMatchingProjgectRoom(@RequestBody RequestProjectRoomDTO newProjectRoom) {

		log.info("registNonMatchingProjgectRoom - 새로운 프로젝트 이름: {}", newProjectRoom.getName());

		projectRoomService.createNonMatchingProject(newProjectRoom);
		return ResponseEntity.ok(Map.of("message", "프로젝트 방이 성공적으로 생성되었습니다!"));
	}

	// TODO. 프로젝트 방 삭제 (팀원 자체 삭제는 불가하도록?)
	

	/* 프로젝트 방 정보 수정
		- 프로젝트 정보(프로젝트명, 한줄 소개, 설명, 프로젝트 링크, 썸네일)
	*/
	@PatchMapping("/{projectRoomId}/edit")
	public ResponseEntity<?> editProjectRoom(
		@PathVariable int projectId,
		@RequestPart("info") ProjectRoomEditDTO projectInfo,
		@RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
			HttpServletRequest request
	) {
		log.info("{} 번 프로젝트 {} 정보 수정  : ", projectId , projectInfo.getName() );

		String token = request.getHeader("Authorization");
		Map<String, Object> userInfo = authService.getCurrentUserInfo();
		int memberId = (int) userInfo.get("id");

		// int memberId = jwtUtil.getId(request.getHeader("Authorization").replace("Bearer ", ""));

		projectRoomService.updateProject(projectId, projectInfo, thumbnailFile, memberId);
		return ResponseEntity.ok().build();
	}

	// 자율 매칭 방 팀원 등록
	// 입장 코드 입력 후 해당 프로젝트 방의 팀원으로 추가
	@PostMapping("/nonMatching/invite")
	public ResponseEntity<?> joinProjectBySessionCode(
		@RequestBody ProjectInviteRequestDTO inviteRequest,
		HttpServletRequest request
		) {
		int memberId = jwtUtil.getId(request.getHeader("Authorization").replace("Bearer ", ""));

		projectRoomService.joinProjectRoom(inviteRequest.getSessionCode(), memberId);
		return ResponseEntity.ok().build();
	}

	// 프로젝트 입장 코드 메일로 초대
	@PostMapping("/invite/send")
	public ResponseEntity<?> sendInviteMail(@RequestBody RequestInviteEmailVO requestVO) {
		invitationService.sendProjectInviteEmail(requestVO);
		return ResponseEntity.ok("초대장이 이메일로 전송되었습니다!");
	}


	//


	//


}


