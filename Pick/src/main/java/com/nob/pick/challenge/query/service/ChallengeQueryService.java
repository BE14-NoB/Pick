package com.nob.pick.challenge.query.service;

import java.util.List;

import com.nob.pick.challenge.query.dto.ChallengeQueryDTO;

public interface ChallengeQueryService {
	List<ChallengeQueryDTO> getAllChallenges();
}
