package com.nob.pick.project.command.application.controller;

import com.nob.pick.project.command.application.service.MeetingService;
import com.nob.pick.project.query.dto.MeetingDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController("CommandMeetingController")
@Slf4j
@RequestMapping("/project")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    // 회의록 저장 (자동 or 수동 모두 처리)
    @PostMapping("/{projectId}/meeting/save")
    public ResponseEntity<?> saveMeeting(
        @PathVariable int projectId,
        @RequestBody MeetingDTO meetingDTO) throws AccessDeniedException {
        log.info("📩 회의록 저장 요청 - 프로젝트: {}, DTO: {}", projectId, meetingDTO);

        meetingService.saveMeeting(meetingDTO);
        
        String message = meetingDTO.isAutoSave()
            ? "자동 저장 완료"
            : "회의록 저장 완료";

        return ResponseEntity.ok().body(message);
    }


    // todo. 회의록 수정



    // todo. 회의록 삭제-> soft delete 후 휴지통 행? 복구 가능 ?

    // todo. 회의록 템플릿 적용
    

    // todo. 회의록 복구



}
