package com.nob.pick.achievement.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.achievement.query.dto.AchievementQueryDTO;

@Mapper
public interface AchievementQueryMapper {
	List<AchievementQueryDTO> selectAllAchievements();
}
