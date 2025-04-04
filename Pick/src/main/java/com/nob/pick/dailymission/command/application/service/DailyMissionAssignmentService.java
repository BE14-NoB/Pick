package com.nob.pick.dailymission.command.application.service;

import com.nob.pick.dailymission.command.domain.aggregate.DailyMission;
import com.nob.pick.dailymission.command.domain.aggregate.MemberDailyMission;
import com.nob.pick.dailymission.command.domain.repository.DailyMissionRepository;
import com.nob.pick.dailymission.command.domain.repository.MemberDailyMissionRepository;
import com.nob.pick.member.command.entity.Member;
import com.nob.pick.member.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyMissionAssignmentService {

	private final DailyMissionRepository dailyMissionRepository;
	private final MemberRepository memberRepository;
	private final MemberDailyMissionRepository memberDailyMissionRepository;

	// 매일 자정에 모든 회원에게 일일 미션을 부여
	@Scheduled(cron = "0 0 0 * * ?")
	public void assignDailyMissionsToAllMembers() {
		List<DailyMission> dailyMissions = dailyMissionRepository.findByIsDeletedFalse();

		List<Member> members = memberRepository.findAll();

		for (Member member : members) {
			for (DailyMission dailyMission : dailyMissions) {
				MemberDailyMission memberDailyMission = new MemberDailyMission();
				memberDailyMission.setMember(member);
				memberDailyMission.setDailyMission(dailyMission);
				memberDailyMission.setIsCompleted(false);
				memberDailyMission.setAcceptedDate(null);
				memberDailyMissionRepository.save(memberDailyMission);
			}
		}
	}

	// // 일주일마다 회원별 일일 미션 삭제
	// @Scheduled(cron = "0 0 0 * * 0")
	// public void deleteOldDailyMissions() {
	// 	// 1주일이 지난 날짜를 계산
	// 	LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
	//
	// 	// LocalDate를 String 형식으로 변환 (yyyy-MM-dd 형식)
	// 	String oneWeekAgoString = oneWeekAgo.format(DateTimeFormatter.ISO_LOCAL_DATE);
	//
	// 	// 변환된 String 값을 이용하여 삭제
	// 	memberDailyMissionRepository.deleteAllByAcceptedDateBefore(oneWeekAgoString);
	// }
}
