package com.nob.pick.project.query.service;

import com.nob.pick.project.query.aggregate.ProjectMeeting;
import com.nob.pick.project.query.aggregate.ProjectMeetingImage;
import com.nob.pick.project.query.aggregate.ProjectMeetingTemplate;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;
import com.nob.pick.project.query.dto.enums.TemplateType;
import com.nob.pick.project.query.mapper.ProjectMeetingMapper;
import com.nob.pick.project.query.dto.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("QueryMeetingService")
public class MeetingServiceImpl implements MeetingService {
    private final ProjectMeetingMapper meetingMapper;

    @Autowired
    public MeetingServiceImpl(ProjectMeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    // 프로젝트별 회의록 목록 조회
    @Override
    public List<MeetingDTO> getMeetingListByProjectId(int projectId) {
        List<ProjectMeeting> meetingList = meetingMapper.selectMeetingListByProjectId(projectId);
        return meetingToDTO(meetingList);
    }

    // 회의록 상세 조회
    @Override
    public MeetingDTO getMeetingByMeetingId(int meetingId) {
        ProjectMeeting meeting = meetingMapper.selectMeetingByMeetingId(meetingId);
        return meetingDetailToDTO(meeting);
    }

    // 회의록 템플릿 목록 조회
    @Override
    public List<MeetingTemplateDTO> getMeetingTemplateList() {
        List<ProjectMeetingTemplate> templateList = meetingMapper.selectMeetingTemplateList();
        return templateListToDTO(templateList);
    }

    // 회의록 템플릿 상세 조회
    @Override
    public MeetingTemplateDTO getMeetingTemplateByTemplateId(int templateId) {
        ProjectMeetingTemplate template = meetingMapper.selectMeetingTemplateByTemplateId(templateId);
        return templateToDTO(template);
    }

    // 회의록 템플릿 타입 검색
    @Override
    public List<MeetingTemplateDTO> getMeetingTemplateListByTypeNum(int typeNum) {
        return List.of();
    }

    // ProjectMeetingTemplate -> MeetingTemplateDTO
    private MeetingTemplateDTO templateToDTO(ProjectMeetingTemplate template) {
        MeetingTemplateDTO dto = new MeetingTemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setContent(template.getContent());
        dto.setType(TemplateType.forNum(template.getType()));
        dto.setDefault(template.isDefault());
        return dto;
    }

    private List<MeetingTemplateDTO> templateListToDTO(List<ProjectMeetingTemplate> templateList) {
        List<MeetingTemplateDTO> meetingTemplateDTOList = new ArrayList<>();
        for (ProjectMeetingTemplate template : templateList) {
            MeetingTemplateDTO meetingTemplateDTO = new MeetingTemplateDTO();
            meetingTemplateDTO.setId(template.getId());
            meetingTemplateDTO.setName(template.getName());
            meetingTemplateDTO.setDescription(template.getDescription());
            meetingTemplateDTO.setContent(template.getContent());
            meetingTemplateDTO.setType(TemplateType.forNum(template.getType()));
            meetingTemplateDTO.setDefault(template.isDefault());

            meetingTemplateDTOList.add(meetingTemplateDTO);
        }
        return meetingTemplateDTOList;
    }

    // ProjectMeeting -> MeetingDTO
    private MeetingDTO meetingDetailToDTO(ProjectMeeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();

        List<ProjectMeetingImage> meetingImages = meetingMapper.selectImagesByMeetingId(meeting.getId());

        meetingDTO.setId(meeting.getId());
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setUploadTime(meeting.getUploadTime().toString());
        meetingDTO.setUpdateTime(meeting.getUpdateTime().toString());
        meetingDTO.setProjectRoomId(meeting.getProjectRoomId());
        meetingDTO.setImages(meetingImages);

        return meetingDTO;
    }

    // List<ProjectMeeting> -> List<MeetingDTO>
    private List<MeetingDTO> meetingToDTO(List<ProjectMeeting> meetingList) {
        List<MeetingDTO> meetingDTOList = new ArrayList<>();

        for(ProjectMeeting meeting : meetingList) {
            MeetingDTO dto = new MeetingDTO();
            List<ProjectMeetingImage> meetingImages = meetingMapper.selectImagesByMeetingId(meeting.getId());
//            String authorName = memberService.findMemberInfoById(meeting.getAuthorId()).getName();

            dto.setId(meeting.getId());
            dto.setTitle(meeting.getTitle());
            dto.setContent(meeting.getContent());
            dto.setAuthorId(meeting.getAuthorId());
//            dto.setAuthorName(authorName);
            dto.setImages(meetingImages);
            dto.setUploadTime(meeting.getUploadTime().toString());
            dto.setUpdateTime(meeting.getUpdateTime().toString());

            meetingDTOList.add(dto);
        }
        return meetingDTOList;
    }
}
