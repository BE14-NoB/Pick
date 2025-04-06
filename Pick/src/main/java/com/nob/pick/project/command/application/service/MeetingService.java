package com.nob.pick.project.command.application.service;

import com.nob.pick.project.query.dto.MeetingDTO;

import java.nio.file.AccessDeniedException;

public interface MeetingService {
    void saveMeeting(MeetingDTO meetingDTO) throws AccessDeniedException;

    MeetingDTO createEmptyMeeting(int projectId, int authorId) throws AccessDeniedException;

    void autoSaveContent(int meetingId, String content);

    String getTemplateContent(String templateId);

    void updateMeetingContent(int meetingId, String templateContent);
}
