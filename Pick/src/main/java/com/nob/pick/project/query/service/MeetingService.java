package com.nob.pick.project.query.service;

import com.nob.pick.project.query.dto.MeetingDTO;
import com.nob.pick.project.query.dto.MeetingTemplateDTO;

import java.util.List;

public interface MeetingService {

    List<MeetingDTO> getMeetingsByProjectId(int projectId);

    MeetingDTO getMeetingsByMeetingId(int meetingId);

	List<MeetingTemplateDTO> getMeetingTemplateList();
}
