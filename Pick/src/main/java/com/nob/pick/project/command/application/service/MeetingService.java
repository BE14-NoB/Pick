package com.nob.pick.project.command.application.service;

import com.nob.pick.project.query.dto.MeetingDTO;

import java.nio.file.AccessDeniedException;

public interface MeetingService {
    void createMeeting(MeetingDTO meetingDTO) throws AccessDeniedException;
}
