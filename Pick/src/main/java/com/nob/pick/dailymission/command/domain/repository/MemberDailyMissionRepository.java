package com.nob.pick.dailymission.command.domain.repository;

import com.nob.pick.dailymission.command.domain.aggregate.MemberDailyMission;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MemberDailyMissionRepository extends JpaRepository<MemberDailyMission, Integer> {
	List<MemberDailyMission> findByMemberId(int memberId);

	boolean existsByMemberIdAndAcceptedDate(int memberId, String today);

	Collection<Object> findByMemberIdAndAcceptedDateBefore(int memberId, String thresholdDate);
}