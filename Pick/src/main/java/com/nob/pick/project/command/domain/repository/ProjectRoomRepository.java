package com.nob.pick.project.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectRoom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ProjectRoomRepository extends JpaRepository <ProjectRoom, Integer> {

	@Query("""
    SELECT pr
        FROM ProjectRoom pr
    WHERE pr.sessionCode IS NOT NULL
      AND pr.isFinished = false
      AND pr.isDeleted = false
      AND pr.startDate <= :cutoffDate
    """)
	List<ProjectRoom> findUnmatchedRoomsBefore(LocalDate cutoffDate);

	Optional<ProjectRoom> findBySessionCode(Integer sessionCode);
}
