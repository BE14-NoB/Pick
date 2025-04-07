package com.nob.pick.dailymission.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.dailymission.query.dto.MemberDailyMissionQueryDTO;
import com.nob.pick.dailymission.query.service.MemberDailyMissionQueryService;

@RestController
@RequestMapping("/daily-mission")
public class MemberDailyMissionQueryController {

	private final MemberDailyMissionQueryService memberDailyMissionQueryService;

	@Autowired
	public MemberDailyMissionQueryController(MemberDailyMissionQueryService memberDailyMissionQueryService) {
		this.memberDailyMissionQueryService = memberDailyMissionQueryService;
	}

	@GetMapping("/{memberId}")
	public List<MemberDailyMissionQueryDTO> getDailyMissionsByMember(@PathVariable("memberId") int memberId) {
		return memberDailyMissionQueryService.getDailyMissionsByMember(memberId);
	}

	@GetMapping("/{memberId}/completed")
	public List<MemberDailyMissionQueryDTO> getCompletedDailyMissionsByMember(@PathVariable("memberId") int memberId) {
		return memberDailyMissionQueryService.getDailyMissionsByMemberWithStatus(memberId, true);
	}

	@GetMapping("/{memberId}/incomplete")
	public List<MemberDailyMissionQueryDTO> getIncompleteDailyMissionsByMember(@PathVariable("memberId") int memberId) {
		return memberDailyMissionQueryService.getDailyMissionsByMemberWithStatus(memberId, false);
	}
}
