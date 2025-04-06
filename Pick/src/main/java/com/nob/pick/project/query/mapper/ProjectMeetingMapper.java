package com.nob.pick.project.query.mapper;

import com.nob.pick.project.query.aggregate.ProjectMeeting;
import com.nob.pick.project.query.aggregate.ProjectMeetingImage;
import com.nob.pick.project.query.aggregate.ProjectMeetingTemplate;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMeetingMapper {
    List<ProjectMeeting> selectMeetingListByProjectId(int projectId);

    List<ProjectMeetingImage> selectImagesByMeetingId(int id);

    ProjectMeeting selectMeetingByMeetingId(int meetingId);

    List<ProjectMeetingTemplate> selectMeetingTemplateList();

    ProjectMeetingTemplate selectMeetingTemplateByTemplateId(int templateId);
}
