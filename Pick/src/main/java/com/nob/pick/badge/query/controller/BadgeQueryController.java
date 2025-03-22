package com.nob.pick.badge.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.badge.query.dto.BadgeQueryDTO;
import com.nob.pick.badge.query.service.BadgeQueryService;

@RestController
@RequestMapping("/badge")
public class BadgeQueryController {

	private final BadgeQueryService badgeQueryService;

	@Autowired
	public BadgeQueryController(BadgeQueryService badgeQueryService) {
		this.badgeQueryService = badgeQueryService;
	}

	@GetMapping("/list")
	public List<BadgeQueryDTO> getBadges() {
		return badgeQueryService.getAllBadges();
	}

	@GetMapping("/{challengeId}")
	public List<BadgeQueryDTO> getBadgesByChallenge(@PathVariable int challengeId) {
		return badgeQueryService.getBadgesByChallengeId(challengeId);
	}
}
