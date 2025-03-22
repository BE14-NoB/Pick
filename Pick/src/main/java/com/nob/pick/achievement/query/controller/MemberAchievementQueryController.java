package com.nob.pick.achievement.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.achievement.query.dto.MemberAchievementQueryDTO;
import com.nob.pick.achievement.query.service.MemberAchievementQueryService;


@RestController
public class MemberAchievementQueryController {
	private final MemberAchievementQueryService memberDailyMissionQueryService;

	@Autowired
	public MemberAchievementQueryController(MemberAchievementQueryService memberAchievementQueryService) {
		this.memberDailyMissionQueryService = memberAchievementQueryService;
	}

	@GetMapping("/achievement/{memberId}")
	public List<MemberAchievementQueryDTO> getAchievementsByMember(@PathVariable int memberId) {
		return memberDailyMissionQueryService.getAchievementsByMember(memberId);
	}

	@GetMapping("/achievement/{memberId}/completed")
	public List<MemberAchievementQueryDTO> getCompletedAchievementsByMember(@PathVariable int memberId) {
		return memberDailyMissionQueryService.getCompletedAchievementsByMember(memberId);
	}

	@GetMapping("/achievement/{memberId}/incomplete")
	public List<MemberAchievementQueryDTO> getIncompleteAchievementsByMember(@PathVariable int memberId) {
		return memberDailyMissionQueryService.getIncompleteAchievementsByMember(memberId);
	}
}
