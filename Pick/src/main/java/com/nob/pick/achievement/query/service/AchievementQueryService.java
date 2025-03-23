package com.nob.pick.achievement.query.service;

import java.util.List;

import com.nob.pick.achievement.query.dto.AchievementQueryDTO;

public interface AchievementQueryService {
	List<AchievementQueryDTO> getAllAchievements();
}
