package com.nob.pick.dailymission.command.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nob.pick.common.util.JwtUtil;
import com.nob.pick.dailymission.command.application.service.MemberDailyMissionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-mission")
public class MemberDailyMissionController {

	private final MemberDailyMissionService memberDailyMissionService;
	private final JwtUtil jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(MemberDailyMissionController.class);

	// 로그인한 회원에게 일일 미션 부여
	@PostMapping("/assign")
	public ResponseEntity<String> assignDailyMissions(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		// Bearer 제거
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		int memberId = jwtUtil.getId(token);
		memberDailyMissionService.assignTodayMissionIfNotYet(memberId);

		return ResponseEntity.ok("일일 미션 부여 완료 또는 이미 부여됨");
	}

	// 회원별 일일 미션 전체 삭제 (하드 딜리트)
	@DeleteMapping("/reset")
	public ResponseEntity<String> resetMemberDailyMissions() {
		memberDailyMissionService.deleteAllMemberDailyMissions();
		return ResponseEntity.ok("모든 회원 일일 미션이 초기화되었습니다.");
	}

	// 일일미션 달성 시 달성여부 변경
	@PutMapping("/complete/{dailyMissionId}")
	public ResponseEntity<String> completeMission(
		@PathVariable int dailyMissionId,
		@RequestHeader("Authorization") String token
	) {
		int memberId = jwtUtil.getId(token.replace("Bearer ", ""));
		memberDailyMissionService.completeMission(dailyMissionId, memberId);
		return ResponseEntity.ok("일일미션을 완료했습니다!");
	}
}
