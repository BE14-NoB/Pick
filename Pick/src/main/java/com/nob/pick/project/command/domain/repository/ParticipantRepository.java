package com.nob.pick.project.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nob.pick.project.command.domain.aggregate.entity.Participant;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectRoom;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    boolean existsByProjectRoomIdAndMemberId(int projectRoomId, int memberId);      // 팀원 여부 확인

    int countByProjectRoomId(int projectRoomId);
}
