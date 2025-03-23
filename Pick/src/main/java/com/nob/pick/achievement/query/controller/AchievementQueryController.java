package com.nob.pick.achievement.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.achievement.query.dto.AchievementQueryDTO;
import com.nob.pick.achievement.query.service.AchievementQueryService;

@RestController
@RequestMapping("/achievement")
public class AchievementQueryController {

	private final AchievementQueryService achievementQueryService;

	@Autowired
	public AchievementQueryController(AchievementQueryService achievementQueryService) {
		this.achievementQueryService = achievementQueryService;
	}

	@GetMapping("/list")
	public List<AchievementQueryDTO> getAchievements() {
		return achievementQueryService.getAllAchievements();
	}
}
