package com.nob.pick.project.command.application.controller;
import com.nob.pick.project.command.application.dto.MemberReviewDTO;
import com.nob.pick.project.command.application.dto.ProjectReviewDTO;
import com.nob.pick.project.command.application.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController("CommandReviewController")
@Slf4j
@RequestMapping("/project")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    /*
        todo. 후기는 한 번 작성하면 수정/삭제 불가능하다고 프론트에서 공지할 것
     */

    // 프로젝트 후기 작성
    @PostMapping("/{projectRoomId}/projectReview")
    public ResponseEntity<?> registProjectReview(@PathVariable int projectRoomId,
                                                 @RequestBody ProjectReviewDTO projectReview) throws AccessDeniedException {
        // url <-> body 매칭 확인
        if (projectReview.getProject_id() != projectRoomId) {
            log.info(String.valueOf(projectReview.getProject_id()), projectRoomId);
            return ResponseEntity.badRequest().body("URL과 body의 프로젝트 아이디가 일치하지 않습니다.");
        }
        // 리뷰 작성
        reviewService.createProjectReview(projectReview);

        return ResponseEntity.ok().build();
    }

    // 팀원 후기 작성
    @PostMapping("/{projectRoomId}/memberReview")
    public ResponseEntity<?> registMemberReview(@PathVariable int projectRoomId,
                                                @RequestBody MemberReviewDTO memberReview) throws AccessDeniedException {
        // url <-> body 매칭 확인
        if (memberReview.getProjectId() != projectRoomId) {
            return ResponseEntity.badRequest().body("URL과 body의 프로젝트 아이디가 일치하지 않습니다.");
        }
        // 리뷰 저장
        reviewService.createMemberReview(memberReview);

        return ResponseEntity.ok().build();
    }

}
