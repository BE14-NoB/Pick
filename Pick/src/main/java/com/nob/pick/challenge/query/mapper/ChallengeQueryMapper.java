package com.nob.pick.challenge.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nob.pick.challenge.query.dto.ChallengeQueryDTO;

@Mapper
public interface ChallengeQueryMapper {

	List<ChallengeQueryDTO> selectAllChallenges();
}
