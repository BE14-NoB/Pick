package com.nob.pick.dailymission.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.dailymission.query.dto.DailyMissionQueryDTO;
import com.nob.pick.dailymission.query.service.DailyMissionQueryService;

@RestController
@RequestMapping("/daily-mission")
public class DailyMissionQueryController {

	private final DailyMissionQueryService dailyMissionQueryService;

	@Autowired
	public DailyMissionQueryController(DailyMissionQueryService dailyMissionQueryService) {
		this.dailyMissionQueryService = dailyMissionQueryService;
	}

	@GetMapping("/list")
	public List<DailyMissionQueryDTO> getDailyMissions() {
		return dailyMissionQueryService.getAllDailyMissions();
	}
}
