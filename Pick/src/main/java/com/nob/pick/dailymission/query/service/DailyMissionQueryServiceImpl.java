package com.nob.pick.dailymission.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.dailymission.query.dto.DailyMissionQueryDTO;
import com.nob.pick.dailymission.query.mapper.DailyMissionQueryMapper;

@Service
public class DailyMissionQueryServiceImpl implements DailyMissionQueryService {

	private final DailyMissionQueryMapper dailyMissionQueryMapper;

	@Autowired
	public DailyMissionQueryServiceImpl(DailyMissionQueryMapper dailyMissionQueryMapper) {
		this.dailyMissionQueryMapper = dailyMissionQueryMapper;
	}

	@Override
	public List<DailyMissionQueryDTO> getAllDailyMissions() {
		return dailyMissionQueryMapper.selectAllDailyMissions();
	}
}
