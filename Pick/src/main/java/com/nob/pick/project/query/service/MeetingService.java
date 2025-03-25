package com.nob.pick.project.query.service;

import com.nob.pick.project.query.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {

    List<MeetingDTO> getMeetingsByProjectId(int projectId);

    MeetingDTO getMeetingsByMeetingId(int meetingId);
}
