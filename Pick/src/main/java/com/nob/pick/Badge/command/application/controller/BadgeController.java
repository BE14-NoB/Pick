package com.nob.pick.badge.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nob.pick.badge.command.application.dto.BadgeAwardDTO;
import com.nob.pick.badge.command.application.dto.BadgeDTO;
import com.nob.pick.badge.command.application.service.BadgeService;
import com.nob.pick.badge.command.domain.aggregate.Badge;

@RestController
@RequestMapping("/badge")
public class BadgeController {

	@Autowired
	private BadgeService badgeService;

	// 뱃지 추가
	@PostMapping("/add")
	public ResponseEntity<Badge> addBadge(@RequestBody BadgeDTO badgeDto) {
		Badge badge = badgeService.addBadge(badgeDto);
		return new ResponseEntity<>(badge, HttpStatus.CREATED);
	}

	// 뱃지 수정
	@PutMapping("/modify/{badgeId}")
	public ResponseEntity<Badge> updateBadge(@PathVariable int badgeId, @RequestBody BadgeDTO badgeDto) {
		Badge badge = badgeService.updateBadge(badgeId, badgeDto);
		return new ResponseEntity<>(badge, HttpStatus.OK);
	}

	// 뱃지 삭제
	@DeleteMapping("/delete/{badgeId}")
	public ResponseEntity<Void> deleteBadge(@PathVariable int badgeId) {
		badgeService.deleteBadge(badgeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// 회원에게 뱃지 부여 및 레벨업
	@PostMapping("/award")
	public ResponseEntity<String> awardBadge(@RequestBody BadgeAwardDTO badgeAwardDTO) {
		badgeService.awardBadgeToMember(badgeAwardDTO.getMemberId(), badgeAwardDTO.getAchievementId());
		return ResponseEntity.ok("뱃지가 부여되었습니다.");
	}
}
