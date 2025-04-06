package com.nob.pick.badge.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;

import com.nob.pick.achievement.command.domain.aggregate.Achievement;
import com.nob.pick.achievement.command.domain.repository.AchievementRepository;
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

	private MemberAchievementRepository memberAchievementRepository;

	@Autowired
	private AchievementRepository achievementRepository;

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
	public void awardBadgeToMember(Long memberId, int achievementId) {
		// 도전 과제 조회
		Achievement achievement = achievementRepository.findById(achievementId)
			.orElseThrow(() -> new IllegalArgumentException("해당 도전 과제를 찾을 수 없습니다."));

		// 도전 과제가 속한 챌린지 가져옴
		Challenge challenge = achievement.getChallenge();

		// 해당 챌린지와 연결된 뱃지 조회
		List<Badge> badges = badgeRepository.findByChallenge(challenge);
		if (badges.isEmpty()) {
			throw new IllegalArgumentException("해당 챌린지에 연결된 뱃지가 없습니다.");
		}

		// 회원별 도전 과제 진행도 가져옴
		MemberAchievement memberAchievement = memberAchievementRepository.findByMemberIdAndAchievementId(memberId,
				achievementId)
			.orElseThrow(() -> new IllegalArgumentException("회원별 도전 과제 정보를 찾을 수 없습니다."));

		int progress = memberAchievement.getProgress(); // 진행도

		// 진행도가 뱃지 레벨업 조건을 만족하면 지급
		for (Badge badge : badges) {
			int requiredProgress = badge.getRequirement();

			// 현재 진행도가 필요 요구 조건의 몇 배인지 확인
			int expectedLevel = progress / requiredProgress;

			// 회원이 해당 뱃지를 보유하고 있는지 확인
			Optional<MemberBadge> existingBadge = memberBadgeRepository.findByMemberIdAndBadgeId(memberId, badge.getId());

			if (existingBadge.isPresent()) {
				// 이미 보유한 경우, 레벨이 expectedLevel보다 낮으면 올려줌
				MemberBadge memberBadge = existingBadge.get();
				if (memberBadge.getLevel() < expectedLevel) {
					memberBadge.setLevel(expectedLevel);
					memberBadgeRepository.save(memberBadge);
				}
			} else {
				// 새로 지급
				if (progress >= requiredProgress) {
					MemberBadge newBadge = new MemberBadge();
					newBadge.setMemberId(memberId);
					newBadge.setBadge(badge);
					newBadge.setAcquiredDate(LocalDateTime.now().toString());
					newBadge.setLevel(expectedLevel);
					memberBadgeRepository.save(newBadge);
				}
			}
		}
	}
}
