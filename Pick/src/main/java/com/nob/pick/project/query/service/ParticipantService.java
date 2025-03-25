package com.nob.pick.project.query.service;

import com.nob.pick.project.query.dto.ParticipantDTO;

import java.util.List;

public interface ParticipantService {
    boolean isProjectParticipant(int projectRoomId, int memberId);

    List<ParticipantDTO>  getParticipantsByProjectId(int projectId);

}
