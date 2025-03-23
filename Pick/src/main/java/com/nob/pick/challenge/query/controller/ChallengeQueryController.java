package com.nob.pick.challenge.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.challenge.query.dto.ChallengeQueryDTO;
import com.nob.pick.challenge.query.service.ChallengeQueryService;

@RestController
@RequestMapping("/challenge")
public class ChallengeQueryController {

	private final ChallengeQueryService challengeQueryService;

	@Autowired
	public ChallengeQueryController(ChallengeQueryService challengeQueryService) {
		this.challengeQueryService = challengeQueryService;
	}

	@GetMapping("/list")
	public List<ChallengeQueryDTO> getChallenges() {
		return challengeQueryService.getAllChallenges();
	}

}
