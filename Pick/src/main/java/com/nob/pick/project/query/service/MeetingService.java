package com.nob.pick.project.query.service;

import com.nob.pick.project.query.dto.MeetingDTO;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;

import java.util.List;

public interface MeetingService {

    List<MeetingDTO> getMeetingListByProjectId(int projectId);

    MeetingDTO getMeetingByMeetingId(int meetingId);

	List<MeetingTemplateDTO> getMeetingTemplateList();

	MeetingTemplateDTO getMeetingTemplateByTemplateId(int templateId);

	List<MeetingTemplateDTO> getMeetingTemplateListByTypeNum(int typeNum);
}
