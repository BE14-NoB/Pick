package com.nob.pick.dailymission.command.application.service;

import com.nob.pick.dailymission.command.application.infrastructure.MemberClient;
import com.nob.pick.dailymission.command.domain.aggregate.DailyMission;
import com.nob.pick.dailymission.command.domain.aggregate.MemberDailyMission;
import com.nob.pick.dailymission.command.domain.repository.DailyMissionRepository;
import com.nob.pick.dailymission.command.domain.repository.MemberDailyMissionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDailyMissionService {

	private final DailyMissionRepository dailyMissionRepository;
	private final MemberDailyMissionRepository memberDailyMissionRepository;
	private final MemberClient memberClient;

	// 로그인한 회원에게 하루에 한 번만 일일 미션을 부여
	@Transactional
	public void assignTodayMissionIfNotYet(int memberId) {
		String today = LocalDate.now().toString();

		// 오늘 부여된 기록이 하나라도 있으면 return
		boolean exists = memberDailyMissionRepository.existsByMemberIdAndAcceptedDate(memberId, today);
		if (exists) return;

		// 삭제되지 않은 일일 미션 전체 조회
		List<DailyMission> missions = dailyMissionRepository.findByIsDeletedFalse();
		for (DailyMission mission : missions) {
			MemberDailyMission newMission = new MemberDailyMission();
			newMission.setMemberId(memberId);
			newMission.setDailyMission(mission);
			newMission.setIsCompleted(false);
			newMission.setAcceptedDate(today);

			memberDailyMissionRepository.save(newMission);
		}
	}

	// 매주 월요일 오전 00:00에 실행
	@Scheduled(cron = "0 0 0 * * MON")
	@Transactional
	public void deleteAllMemberDailyMissions() {
		log.info("회원별 일일 미션 전체 삭제 시작");
		memberDailyMissionRepository.deleteAll();
		log.info("회원별 일일 미션 전체 삭제 완료");
	}
}
