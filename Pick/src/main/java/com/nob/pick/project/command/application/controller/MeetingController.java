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

    // 회의록 생성
    @PostMapping("/{projectId}/meeting/new")
    public ResponseEntity<MeetingDTO> createEmptyMeeting(
        @PathVariable("projectId") int projectId,
        @RequestParam int authorId
        ) throws AccessDeniedException {
        MeetingDTO meetingDTO = meetingService.createEmptyMeeting(projectId, authorId);
        return ResponseEntity.ok(meetingDTO);
    }

    // 회의록 자동 저장
    @PatchMapping("/meeting/{meetingId}/autosave")
    public ResponseEntity<?> autoSaveMeeting(@PathVariable int meetingId, @RequestBody String content) {
        meetingService.autoSaveContent(meetingId, content);
        return ResponseEntity.ok().body("자동 저장됨");
    }

    // 템플릿 적용
    @PostMapping("/meeting/{meetingId}/apply-template")
    public ResponseEntity<?> applyTemplate(
        @PathVariable int meetingId,
        @RequestParam String templateId
    ) {
        String templateContent = meetingService.getTemplateContent(templateId);
        meetingService.updateMeetingContent(meetingId, templateContent);
        return ResponseEntity.ok().body("템플릿 적용 완료");
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
