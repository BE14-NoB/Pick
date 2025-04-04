package com.nob.pick.achievement.command.application.service;

import com.nob.pick.achievement.command.application.dto.AchievementCreateRequestDTO;
import com.nob.pick.achievement.command.application.dto.AchievementUpdateRequestDTO;
import com.nob.pick.achievement.command.domain.aggregate.Achievement;
import com.nob.pick.achievement.command.domain.aggregate.MemberAchievement;
import com.nob.pick.achievement.command.domain.repository.AchievementRepository;
import com.nob.pick.achievement.command.domain.repository.MemberAchievementRepository;
import com.nob.pick.challenge.command.domain.aggregate.Challenge;
import com.nob.pick.challenge.command.domain.repository.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

	private final AchievementRepository achievementRepository;
	private final ChallengeRepository challengeRepository;
	private final MemberAchievementRepository memberAchievementRepository;

	// 도전 과제 추가
	@Transactional
	public Achievement createAchievement(AchievementCreateRequestDTO dto) {
		Challenge challenge = challengeRepository.findById(dto.getChallengeId())
			.orElseThrow(() -> new EntityNotFoundException("챌린지를 찾을 수 없습니다."));

		Achievement achievement = dto.toEntity(challenge);
		return achievementRepository.save(achievement);
	}

	// 도전 과제 수정
	@Transactional
	public Achievement updateAchievement(Integer id, AchievementUpdateRequestDTO dto) {
		Achievement achievement = achievementRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("도전 과제를 찾을 수 없습니다."));

		achievement.update(dto.getRequirement(), dto.getExp());
		return achievement;
	}

	// 도전 과제 삭제
	@Transactional
	public void deleteAchievement(Integer id) {
		Achievement achievement = achievementRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("도전 과제를 찾을 수 없습니다."));

		achievement.softDelete();
	}

	// 회원별 도전 과제 진행도 업데이트
	@Transactional
	public void updateMemberAchievementProgress(Long memberId, int achievementId, Integer progressIncrement) {
		MemberAchievement memberAchievement = memberAchievementRepository.findByMemberIdAndAchievementId(memberId, achievementId)
			.orElseThrow(() -> new EntityNotFoundException("회원별 도전 과제를 찾을 수 없습니다."));

		Integer newProgress = memberAchievement.getProgress() + progressIncrement;
		memberAchievement.setProgress(newProgress);

		Integer requirement = memberAchievement.getAchievement().getRequirement();
		Integer newAchievedTime = newProgress / requirement;
		memberAchievement.setAchievedTime(newAchievedTime);

		memberAchievementRepository.save(memberAchievement);
	}
}
