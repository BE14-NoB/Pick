package com.nob.pick.project.query.service;

import com.nob.pick.project.query.mapper.ParticipantMapper;
import com.nob.pick.project.query.dto.ParticipantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantMapper participantMapper;

    @Autowired
    public ParticipantServiceImpl(ParticipantMapper participantMapper) {
        this.participantMapper = participantMapper;
    }

    // 팀원 여부 체크
    @Override
    public boolean isProjectParticipant(int projectRoomId, int memberId) {
        int count = participantMapper.isProjectParticipant(projectRoomId, memberId);

        return count > 0;
    }

    @Override
    public List<ParticipantDTO> getParticipantsByProjectId(int projectId) {
		return participantMapper.selectParticipantsByProjectRoomId(projectId);
    }



}
