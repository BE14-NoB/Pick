package com.nob.pick.badge.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.badge.query.dto.MemberBadgeQueryDTO;
import com.nob.pick.badge.query.service.MemberBadgeQueryService;

@RestController
@RequestMapping("/badge")
public class MemberBadgeQueryController {
	private final MemberBadgeQueryService memberBadgeQueryService;

	@Autowired
	public MemberBadgeQueryController(MemberBadgeQueryService memberBadgeQueryService) {
		this.memberBadgeQueryService = memberBadgeQueryService;
	}

	@GetMapping("/accepted/{memberId}")
	public List<MemberBadgeQueryDTO> getBadgesByMember(@PathVariable int memberId) {
		return memberBadgeQueryService.getBadgesByMember(memberId);
	}

	@GetMapping("/advantage/{memberId}")
	public int getTotalAdvantage(@PathVariable int memberId) {
		return memberBadgeQueryService.getTotalAdvantageByMemberId(memberId);
	}
}
