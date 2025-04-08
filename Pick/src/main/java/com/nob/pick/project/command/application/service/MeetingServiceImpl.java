package com.nob.pick.project.command.application.service;

import com.nob.pick.project.command.domain.aggregate.entity.Participant;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectMeeting;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectMeetingTemplate;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectRoom;
import com.nob.pick.project.command.domain.repository.MeetingRepository;
import com.nob.pick.project.command.domain.repository.ParticipantRepository;
import com.nob.pick.project.command.domain.repository.ProjectRoomRepository;
import com.nob.pick.project.command.domain.repository.TemplateRepository;
import com.nob.pick.project.query.dto.MeetingDTO;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.awt.*;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service("CommandMeetingService")
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final ProjectRoomRepository projectRoomRepository;
    private final TemplateRepository templateRepository;

    @Autowired
    public MeetingServiceImpl(
        MeetingRepository meetingRepository,
        ParticipantRepository participantRepository,
        ProjectRoomRepository projectRoomRepository,
		TemplateRepository templateRepository)
    {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
        this.projectRoomRepository = projectRoomRepository;
		this.templateRepository = templateRepository;
	}
    
    // 빈 회의록 생성
    @Override
    @Transactional
    public MeetingDTO createEmptyMeeting(int projectId, int authorId) throws AccessDeniedException {
        ProjectRoom projectRoom = projectRoomRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        Participant author = validateParticipant(projectId, authorId, "작성자는 프로젝트 팀원이 아닙니다.");

        ProjectMeeting emptyMeeting = ProjectMeeting.builder()
            .title(null)
            .content(" ")
            .participant(author)
            .projectRoom(projectRoom)
            .uploadTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();

        ProjectMeeting saved = meetingRepository.save(emptyMeeting);
        // Meeting -> MeetingDTO
        return MeetingToDTO(saved);
    }


    @Override
    public String getTemplateContent(int templateId) {
        ProjectMeetingTemplate template = templateRepository.findById(templateId)
            .orElseThrow(() -> new EntityNotFoundException("템플릿이 존재하지 않습니다."));
        return template.getContent();
    }

    @Override
    public void updateMeetingContent(int meetingId, String templateContent) {

    }

    // 회의록 내용 수정
    @Override
    @Transactional
    public void applyTemplateContent(int meetingId, String templateContent) {
        ProjectMeeting meeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new EntityNotFoundException("회의록이 존재하지 않습니다."));

        meeting.applyTemplate(templateContent);
        meetingRepository.save(meeting);
    }
    
    // 회의록 삭제
    @Override
    @Transactional
    public void deleteMeeting(int meetingId) {
        ProjectMeeting meeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new EntityNotFoundException("회의록이 존재하지 않습니다."));
        meeting.softDelete();
        meetingRepository.save(meeting);
    }

    // 회의록 복구
    @Override
    @Transactional
    public void restoreMeeting(int meetingId) {
        ProjectMeeting meeting = meetingRepository.findById(meetingId)
            .orElseThrow(() -> new EntityNotFoundException("회의록이 존재하지 않습니다."));

        meeting.restore();
        meetingRepository.save(meeting);
    }



    // 회의록 생성
    @Override
    @Transactional
    public void saveMeeting(MeetingDTO meetingDTO) throws AccessDeniedException {
        log.info("[미팅 생성 요청] meetingDTO = {}", meetingDTO);

        // 팀원 여부 체크
        int authorId = meetingDTO.getAuthorId();
        int projectRoomId = meetingDTO.getProjectRoomId();

        ProjectRoom projectRoom = projectRoomRepository.findById(projectRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다. projectRoomId=" + projectRoomId));


        Participant author = validateParticipant(projectRoomId, authorId, "작성자는 프로젝트 팀원이 아닙니다.");

        ProjectMeeting projectMeeting = ProjectMeeting.builder()
                .title(meetingDTO.getTitle())
                .content(meetingDTO.getContent())
                .updateTime(LocalDateTime.now())
                .uploadTime(LocalDateTime.now())
                .participant(author)
                .projectRoom(projectRoom)
                .build();

        log.info("[생성된 회의록] : {}", projectMeeting.toString());
        try {
            ProjectMeeting savedProjectMeeting = meetingRepository.save(projectMeeting);
            log.info("[회의록 저장 성공] : {}", savedProjectMeeting);
        } catch (Exception e) {
            log.error("[회의록 저장 실패] projectRoomId={}, authorId={}, exception=", projectRoomId, authorId, e);
            e.printStackTrace(); // 콘솔에서도 전체 trace 확인
            throw new IllegalStateException("회의록 저장 중 오류 발생", e);
        }

    }


    private MeetingDTO MeetingToDTO(ProjectMeeting saved) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setTitle(saved.getTitle());
        meetingDTO.setContent(saved.getContent());
        meetingDTO.setUploadTime(saved.getUploadTime().toString());
        meetingDTO.setUpdateTime(saved.getUpdateTime().toString());

        return meetingDTO;

    }



    private Participant validateParticipant(int projectRoomId, int participantId, String errorMessage) throws AccessDeniedException {
        boolean isParticipant = participantRepository.existsByProjectRoomIdAndMemberId(projectRoomId, participantId);
        if (!isParticipant) {
            log.warn("[참여자 검증 실패] {} - projectRoomId={}, participantId={}", errorMessage, projectRoomId, participantId);
            throw new AccessDeniedException(errorMessage);
        }

        return participantRepository.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("참여자를 찾을 수 없습니다. participantId=" + participantId));
    }

    


}
