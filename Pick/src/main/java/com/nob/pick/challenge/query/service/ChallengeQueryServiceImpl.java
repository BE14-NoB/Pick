package com.nob.pick.challenge.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nob.pick.challenge.query.dto.ChallengeQueryDTO;
import com.nob.pick.challenge.query.mapper.ChallengeQueryMapper;

@Service
public class ChallengeQueryServiceImpl implements ChallengeQueryService {

	private final ChallengeQueryMapper challengeQueryMapper;

	@Autowired
	public ChallengeQueryServiceImpl(ChallengeQueryMapper challengeQueryMapper) {
		this.challengeQueryMapper = challengeQueryMapper;
	}

	@Override
	public List<ChallengeQueryDTO> getAllChallenges() {
		return challengeQueryMapper.selectAllChallenges();
	}
}
