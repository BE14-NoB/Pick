package com.nob.pick.project.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.project.command.application.service.ProjectRoomServiceImpl;
import com.nob.pick.project.command.application.dto.RequestProjectRoomDTO;

import lombok.extern.slf4j.Slf4j;

@RestController("CommandProjectRoomController")
@Slf4j
@RequestMapping("/project")
public class ProjectRoomController {
	private final ProjectRoomServiceImpl projectRoomService;

	@Autowired
	public ProjectRoomController(ProjectRoomServiceImpl projectRoomService) {
		this.projectRoomService = projectRoomService;
	}

	/*  (자동 매칭 방)
	 * 	매칭 완료 시 방 생성
	 * 	세션 코드 없음.
	 * 	팀원 맵핑 필요. (공통)
	 * */
	@PostMapping("/matching")
	public ResponseEntity<?> registMathingProjectRoom(@RequestBody RequestProjectRoomDTO newProjectRoom) {
		// 세션 코드 없음
		log.info("registNonMatchingProjgectRoom - 새로운 프로젝트 이름: {}", newProjectRoom.getName());

		projectRoomService.createMatchingProject(newProjectRoom);
		return ResponseEntity.ok().build();
	}

	//  자율 매칭 프로젝트 방 생성
	@PostMapping("/nonMatching")
	public ResponseEntity<?> registNonMatchingProjgectRoom(@RequestBody RequestProjectRoomDTO newProjectRoom) {

		log.info("registNonMatchingProjgectRoom - 새로운 프로젝트 이름: {}", newProjectRoom.getName());

		projectRoomService.createNonMatchingProject(newProjectRoom);
		return ResponseEntity.ok().build();
	}








}


