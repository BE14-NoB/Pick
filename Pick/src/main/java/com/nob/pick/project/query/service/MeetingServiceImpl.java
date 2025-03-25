package com.nob.pick.project.query.service;

import com.nob.pick.project.query.aggregate.ProjectMeeting;
import com.nob.pick.project.query.aggregate.ProjectMeetingImage;
import com.nob.pick.project.query.mapper.ProjectMeetingMapper;
import com.nob.pick.project.query.dto.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final ProjectMeetingMapper meetingMapper;

    @Autowired
    public MeetingServiceImpl(ProjectMeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    @Override
    public List<MeetingDTO> getMeetingsByProjectId(int projectId) {
        List<ProjectMeeting> meetingList = meetingMapper.selectMeetingsByProjectId(projectId);
        return meetingToDTO(meetingList);
    }

    @Override
    public MeetingDTO getMeetingsByMeetingId(int meetingId) {
        ProjectMeeting meeting = meetingMapper.selectMeetingByMeetingId(meetingId);
        return meetingDetailToDTO(meeting);
    }

    // ProjectMeeting -> MeetingDTO
    private MeetingDTO meetingDetailToDTO(ProjectMeeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();

        List<ProjectMeetingImage> meetingImages = meetingMapper.selectImagesByMeetingId(meeting.getId());

        meetingDTO.setId(meeting.getId());
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setUploadTime(meeting.getUploadTime().toString());
        meetingDTO.setUpdateTime(meeting.getUpdateTime().toString());
        meetingDTO.setProjectId(meeting.getProjectRoomId());
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
