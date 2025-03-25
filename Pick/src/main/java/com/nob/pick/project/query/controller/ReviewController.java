package com.nob.pick.project.query.controller;

import com.nob.pick.project.query.aggregate.MemberReview;
import com.nob.pick.project.query.aggregate.ProjectReview;
import com.nob.pick.project.query.service.ParticipantService;
import com.nob.pick.project.query.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j

public class ReviewController {
    private final ReviewService reviewService;
    private final ParticipantService participantService;


    @Autowired
    public ReviewController(ReviewService reviewService, ParticipantService participantService) {
        this.reviewService = reviewService;
        this.participantService = participantService;
    }

    @GetMapping("/project/{projectId}/projectReviews")
    public ResponseEntity<?> getProjectReviews(@PathVariable("projectId") int projectId,
                                               @RequestParam("memberId") int memberId) {
        log.info("프로젝트 후기 목록 조회: projectId={}, memberId={}", projectId, memberId);

        boolean isParticipant = participantService.isProjectParticipant(projectId, memberId);
        if (!isParticipant) {
            log.warn("팀원 아님: memberId={}, projectId={}", memberId, projectId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀원만 접근 가능합니다.");
        }

        List<ProjectReview> projectReviewList = reviewService.getProjectReviews(projectId);
        return ResponseEntity.ok(projectReviewList);

    }

    @GetMapping("/project/{projectId}/MemberReviews")
    public ResponseEntity<?> getMemberReviews(@PathVariable("projectId") int projectId,
                                               @RequestParam("memberId") int memberId) {
        log.info("프로젝트 후기 목록 조회: projectId={}, memberId={}", projectId, memberId);

        boolean isParticipant = participantService.isProjectParticipant(projectId, memberId);
        if (!isParticipant) {
            log.warn("팀원 아님: memberId={}, projectId={}", memberId, projectId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀원만 접근 가능합니다.");
        }

        List<MemberReview> projectReviewList = reviewService.getMemberReviews(projectId);
        return ResponseEntity.ok(projectReviewList);

    }

}
