package com.nob.pick.badge.command.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;

import com.nob.pick.badge.command.application.dto.BadgeDTO;
import com.nob.pick.badge.command.domain.aggregate.Badge;
import com.nob.pick.badge.command.domain.aggregate.MemberBadge;
import com.nob.pick.badge.command.domain.repository.BadgeRepository;
import com.nob.pick.badge.command.domain.repository.MemberBadgeRepository;
import com.nob.pick.achievement.command.domain.aggregate.MemberAchievement;
import com.nob.pick.achievement.command.domain.repository.MemberAchievementRepository;
import com.nob.pick.challenge.command.domain.aggregate.Challenge;
import com.nob.pick.challenge.command.domain.repository.ChallengeRepository;

@Service
public class BadgeService {

	@Autowired
	private BadgeRepository badgeRepository;

	@Autowired
	private ChallengeRepository challengeRepository;

	@Autowired
	private MemberBadgeRepository memberBadgeRepository;


	@Autowired
	private MemberAchievementRepository memberAchievementRepository;

	public Badge addBadge(BadgeDTO badgeDto) {
		Challenge challenge = challengeRepository.findById(badgeDto.getChallengeId())
			.orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));

		Badge badge = new Badge();
		badge.setRequirement(badgeDto.getRequirement());
		badge.setAdvantage(badgeDto.getAdvantage());
		badge.setDescription(badgeDto.getDescription());
		badge.setIsDeleted(false);
		badge.setChallenge(challenge);

		return badgeRepository.save(badge);
	}

	public Badge updateBadge(int badgeId, BadgeDTO badgeDto) {
		Badge badge = badgeRepository.findById(badgeId)
			.orElseThrow(() -> new IllegalArgumentException("뱃지를 찾을 수 없습니다."));

		badge.setRequirement(badgeDto.getRequirement());
		badge.setAdvantage(badgeDto.getAdvantage());
		badge.setDescription(badgeDto.getDescription());

		if (badgeDto.getChallengeId() != null) {
			Challenge challenge = challengeRepository.findById(badgeDto.getChallengeId())
				.orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));
			badge.setChallenge(challenge);
		}

		return badgeRepository.save(badge);
	}

	public void deleteBadge(int badgeId) {
		Badge badge = badgeRepository.findById(badgeId)
			.orElseThrow(() -> new IllegalArgumentException("뱃지를 찾을 수 없습니다."));

		badge.setIsDeleted(true);
		badgeRepository.save(badge);
	}

	// 회원에게 뱃지 부여 및 레벨업
	public void awardBadgeToMember(Long memberId, int badgeId) {
		// Member와 Badge 객체를 각각 조회
		// Member member = memberRepository.findById(memberId)
		// 	.orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

		Badge badge = badgeRepository.findById(badgeId)
			.orElseThrow(() -> new IllegalArgumentException("뱃지를 찾을 수 없습니다."));

		// 회원별 뱃지 조회
		Optional<MemberBadge> existingBadge = memberBadgeRepository.findByMemberIdAndBadgeId(memberId, badgeId);
		if (existingBadge.isPresent()) {
			// 이미 보유하고 있으면 레벨업 처리
			MemberBadge badgeToUpdate = existingBadge.get();
			badgeToUpdate.setLevel(badgeToUpdate.getLevel() + 1);
			memberBadgeRepository.save(badgeToUpdate);
		} else {
			// 새로운 뱃지 부여
			MemberBadge newBadge = new MemberBadge();
			newBadge.setMember(memberId);  // Member 객체 설정
			newBadge.setBadge(badge);    // Badge 객체 설정
			newBadge.setLevel(1);        // 레벨 1로 설정
			newBadge.setAcquiredDate("2025-03-25");  // 획득 날짜 설정 (예시)
			memberBadgeRepository.save(newBadge);
		}
	}

	// 도전 과제 완료 여부 확인
	private boolean isAchievementCompleted(List<MemberAchievement> memberAchievements) {
		return memberAchievements.size() >= 10; // 예시: 10번 이상 완료하면 완료
	}

	// 도전과제 ID로 뱃지 찾기
	private Badge getBadgeByAchievementId(int achievementId) {
		// 도전 과제(Challenge) 조회
		Challenge challenge = challengeRepository.findById(achievementId)
			.orElseThrow(() -> new IllegalArgumentException("해당 도전과제를 찾을 수 없습니다."));

		// Challenge에 연결된 Badge 조회
		return badgeRepository.findByChallengeId(challenge.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 도전과제에 대한 뱃지를 찾을 수 없습니다."));
	}

	// 레벨 업 조건 확인
	private boolean isLevelUpConditionMet(MemberBadge memberBadge) {
		return memberBadge.getLevel() == 1; // 레벨 1에서 2로 업그레이드
	}
}
