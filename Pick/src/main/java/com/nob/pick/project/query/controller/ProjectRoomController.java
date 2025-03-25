package com.nob.pick.project.query.controller;

import com.nob.pick.project.query.dto.MeetingDTO;
import com.nob.pick.project.query.dto.ParticipantDTO;
import com.nob.pick.project.query.dto.ProjectRoomDTO;
import com.nob.pick.project.query.service.MeetingService;
import com.nob.pick.project.query.service.ParticipantService;
import com.nob.pick.project.query.service.ProjectRoomService;
import com.nob.pick.project.query.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectRoomController {
    private final ProjectRoomService projectRoomService;
    private final ParticipantService participantService;
    private final MeetingService meetingService;

    @Autowired
    public ProjectRoomController(ProjectRoomService projectRoomService,
                                 ParticipantService participantService,
                                 MeetingService meetingService) {
        this.projectRoomService = projectRoomService;
        this.participantService = participantService;
        this.meetingService = meetingService;
    }

    // 프로젝트 목록 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<ResponseProjectVO>> getAllProjects() {
        List<ProjectRoomDTO> dtoProjects = projectRoomService.getAllProjects();
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }


    // 회원이 참여한 프로젝트 목록 조회
    @GetMapping("/myProjects")
    public ResponseEntity<List<ResponseProjectVO>> getMyProjects(@RequestParam int memberId) {
        log.info("회원별 프로젝트 조회 요청: memberId={}", memberId);

        List<ProjectRoomDTO> dtoProjects = projectRoomService.getProjectsByMemberId(memberId);
        if (dtoProjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);
        return ResponseEntity.ok(result);
    }

    // 완료된 프로젝트 목록 전체 조회
    @GetMapping("/finishedProjects")
    public ResponseEntity<List<ResponseProjectVO>> getFinishedProjects() {
        List<ProjectRoomDTO> dtoProjects = projectRoomService.getFinishedProjects();
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }

    // 완료된 프로젝트 목록 상세 조회
    @GetMapping("/finishedProjects/{projectId}")
    public ResponseEntity<ResponseFinishedProjectDetailVO> getProjectProjectDetail(@PathVariable int projectId) {
        ProjectRoomDTO detailedProject = projectRoomService.getFinishedProjectDetail(projectId);
        List<ParticipantDTO> participantDTOList = participantService.getParticipantsByProjectId(projectId);

        List<ResponseParticipantVO> participantVOList = participantDTOToVO(participantDTOList);

        ResponseFinishedProjectDetailVO result = projectRoomToDetailDTO(detailedProject);
        result.setParticipants(participantVOList);

        return ResponseEntity.ok().body(result);
    }

    // 팀원 모집 중인 프로젝트 목록 조회
    @GetMapping("/matchingProjects")
    public ResponseEntity<List<ResponseProjectVO>> getMatchingProjects() {
        List<ProjectRoomDTO> dtoProjects = projectRoomService.getmatchingProjects();
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }
    
    // 진행 중인 프로젝트 목록 조회
    @GetMapping("/activeProjects")
    public ResponseEntity<List<ResponseProjectVO>> getActiveProjects() {
        List<ProjectRoomDTO> dtoProjects = projectRoomService.getActiveProjects();
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/activeProjects/{projectId}/enter")
    public ResponseEntity<?> enterActiveProjectRoom(@PathVariable int projectId,
                                                    @RequestBody RequestEnterProject request) {

        log.info("진행 중 프로젝트 방 입장 시도: projectId={}, memberId={}, sessionCode={}", projectId, request.getMemberId(), request.getSessionCode());

        boolean isParticipant = participantService.isProjectParticipant(projectId, request.getMemberId());
        if (!isParticipant) {
            log.warn("팀원 아님: memberId={}, projectId={}", request.getMemberId(), projectId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("팀원만 접근 가능합니다.");
        }

        ProjectRoomDTO projectRoom = projectRoomService.getActiveProjectDetail(projectId);
        if (projectRoom == null) {
            log.warn("프로젝트 없음 - 잘못된 ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 프로젝트입니다.");
        }

        if (projectRoom.getSessionCode() != null) {
            if (request.getSessionCode() == null || !projectRoom.getSessionCode().equals(request.getSessionCode())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 코드가 잘못되었습니다.");
            }
        }

        // 팀원 정보 가져오기
        List<ParticipantDTO> participantDTOList = participantService.getParticipantsByProjectId(projectId);
        List<ResponseParticipantVO> participantVOList = participantDTOToVO(participantDTOList);

        // 회의록 정보 가져오기
        List<MeetingDTO> meetingList = meetingService.getMeetingsByProjectId(projectId);

        ResponseActiveProjectRoomVO enteredProject = new ResponseActiveProjectRoomVO();

        enteredProject.setId(projectRoom.getId());
        enteredProject.setName(projectRoom.getName());
        enteredProject.setMaximumParticipant(projectRoom.getMaximumParticipant());
        enteredProject.setContent(projectRoom.getContent());
        enteredProject.setStartDate(projectRoom.getStartDate());
        enteredProject.setEndDate(projectRoom.getEndDate());

        enteredProject.setSessionCode(projectRoom.getSessionCode());

        enteredProject.setProjectUrl(projectRoom.getProjectUrl());
        enteredProject.setThumbnailImage(projectRoom.getThumbnailImage());
        enteredProject.setIntroduction(projectRoom.getIntroduction());

        enteredProject.setParticipants(participantVOList);

        enteredProject.setDurationTime(projectRoom.getDurationTime());
        enteredProject.setTechnologyCategoryId(projectRoom.getTechnologyCategoryId());
        enteredProject.setTechnologyCategoryName(projectRoom.getTechnologyCategoryName());

        enteredProject.setMeetingNotes(meetingList);


        log.info("프로젝트 방 입장 성공 memberId={}, projectId={}", request.getMemberId(), projectId);

        return ResponseEntity.ok(enteredProject);
    }

    // 삭제된 프로젝트 목록 조회
    @GetMapping("/deletedProjects")
    public ResponseEntity<List<ResponseProjectVO>> getDeletedProjects() {
        List<ProjectRoomDTO> dtoProjects = projectRoomService.getDeletedProjects();
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<ResponseProjectVO>> getSearchedProjectsByName(@RequestParam("name") String searchName) {
        log.info("프로젝트 이름 검색: {}", searchName);

        List<ProjectRoomDTO> dtoProjects = projectRoomService.getSearchedProjectsByName(searchName);
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/searchByTech")
    public ResponseEntity<List<ResponseProjectVO>> getSearchedProjectsByName(@RequestParam("techIds") List<Integer> technologyCategoryIds) {
        log.info("기술 분류 검색: {}", technologyCategoryIds);

        List<ProjectRoomDTO> dtoProjects = projectRoomService.getSearchedProjectsByTech(technologyCategoryIds);
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/searchByBoth")
    public ResponseEntity<List<ResponseProjectVO>> getSearchedProjectsByTechAndName(@RequestParam("techId") int categoryId,
                                                                                    @RequestParam("name") String searchName) {
        log.info("카테고리 ID: {}, 검색어: {}", categoryId, searchName);

        List<ProjectRoomDTO> dtoProjects = projectRoomService.getSearchedProjectsByTechAndName(categoryId, searchName);
        List<ResponseProjectVO> result = projectRoomDTOToVO(dtoProjects);

        return ResponseEntity.ok().body(result);
    }



    // List<ProjecRoomDTO> -> List<ResponseProjectVO>
    private List<ResponseProjectVO> projectRoomDTOToVO(List<ProjectRoomDTO> ProjectRoomDTOList) {
        List<ResponseProjectVO> responseList = new ArrayList<>();

        for(ProjectRoomDTO dto : ProjectRoomDTOList) {
            ResponseProjectVO vo = new ResponseProjectVO();
            vo.setId(dto.getId());
            vo.setName(dto.getName());
            vo.setStartDate(dto.getStartDate());
            vo.setEndDate(dto.getEndDate());
            vo.setDurationTime(dto.getDurationTime());
            vo.setMaximumParticipant(dto.getMaximumParticipant());
            vo.setThumbnailImage(dto.getThumbnailImage());
            vo.setIntroduction(dto.getIntroduction());
            vo.setTechnologyCategoryId(dto.getTechnologyCategoryId());
            vo.setTechnologyCategoryName(dto.getTechnologyCategoryName());

            responseList.add(vo);
        }
        return responseList;
    }

    // ProjectRoom -> ProjectRoomDTO
    private ResponseFinishedProjectDetailVO projectRoomToDetailDTO(ProjectRoomDTO projectRoom) {
        ResponseFinishedProjectDetailVO vo = new ResponseFinishedProjectDetailVO();

        System.out.println(projectRoom);

        vo.setId(projectRoom.getId());
        vo.setName(projectRoom.getName());
        vo.setContent(projectRoom.getContent());
        vo.setStartDate(projectRoom.getStartDate());
        vo.setEndDate(projectRoom.getEndDate());
        vo.setDurationTime(projectRoom.getDurationTime());
        vo.setMaximumParticipant(projectRoom.getMaximumParticipant());
        vo.setIntroduction(projectRoom.getIntroduction());
        vo.setThumbnailImage(projectRoom.getThumbnailImage());

        vo.setTechnologyCategoryId(projectRoom.getTechnologyCategoryId());
        vo.setTechnologyCategoryName(projectRoom.getTechnologyCategoryName());
        return vo;
    }

    // ParticipantDTO -> ResponseParticipantVO
    private List<ResponseParticipantVO> participantDTOToVO(List<ParticipantDTO> participantDTOList) {
        List<ResponseParticipantVO> voList = new ArrayList<>();

        for (ParticipantDTO dto : participantDTOList) {
            ResponseParticipantVO vo = new ResponseParticipantVO();
            vo.setParticipantId(dto.getId());
            vo.setManager(dto.isManager());
            vo.setMemberName(dto.getMemberName());
            vo.setMemberNickname(dto.getMemberNickname());

            voList.add(vo);
        }

        return voList;
    }


}
