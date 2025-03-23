package com.nob.pick.dailymission.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.dailymission.query.dto.DailyMissionQueryDTO;

@Mapper
public interface DailyMissionQueryMapper {
	List<DailyMissionQueryDTO> selectAllDailyMissions();
}
