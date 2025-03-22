package com.nob.pick.dailymission.query.service;

import java.util.List;

import com.nob.pick.dailymission.query.dto.DailyMissionQueryDTO;

public interface DailyMissionQueryService {
	List<DailyMissionQueryDTO> getAllDailyMissions();
}
