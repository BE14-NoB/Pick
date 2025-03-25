package com.nob.pick.project.query.mapper;

import com.nob.pick.project.query.dto.ParticipantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParticipantMapper {
    int isProjectParticipant(int projectRoomId, int memberId);

    List<ParticipantDTO> selectParticipantsByProjectRoomId(int projectId);
}
