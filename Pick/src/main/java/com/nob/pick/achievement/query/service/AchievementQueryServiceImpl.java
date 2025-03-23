package com.nob.pick.achievement.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.achievement.query.dto.AchievementQueryDTO;
import com.nob.pick.achievement.query.mapper.AchievementQueryMapper;

@Service
public class AchievementQueryServiceImpl implements AchievementQueryService {

	private final AchievementQueryMapper achievementQueryMapper;

	@Autowired
	public AchievementQueryServiceImpl(AchievementQueryMapper achievementQueryMapper) {
		this.achievementQueryMapper = achievementQueryMapper;
	}

	@Override
	public List<AchievementQueryDTO> getAllAchievements() {
		return achievementQueryMapper.selectAllAchievements();
	}
}
