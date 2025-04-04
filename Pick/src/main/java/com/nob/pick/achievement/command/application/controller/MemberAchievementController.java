package com.nob.pick.achievement.command.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.achievement.command.application.dto.ProgressUpdateRequestDTO;
import com.nob.pick.achievement.command.application.service.AchievementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member-achievement")
@RequiredArgsConstructor
public class MemberAchievementController {

	private final AchievementService achievementService;

	// 회원별 도전 과제 진행도 갱신
	@PutMapping("/update-progress/{memberId}/{achievementId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateMemberAchievementProgress(
		@PathVariable Long memberId,
		@PathVariable int achievementId,
		@RequestBody ProgressUpdateRequestDTO progressUpdateRequestDTO) {

		achievementService.updateMemberAchievementProgress(
			memberId,
			achievementId,
			progressUpdateRequestDTO.getProgressIncrement());
	}
}
