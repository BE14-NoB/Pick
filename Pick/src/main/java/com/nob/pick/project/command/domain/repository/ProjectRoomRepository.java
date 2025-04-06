package com.nob.pick.project.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nob.pick.project.command.domain.aggregate.entity.ProjectRoom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ProjectRoomRepository extends JpaRepository <ProjectRoom, Integer> {

	@Query("""
    SELECT pr
        FROM ProjectRoom pr
    WHERE pr.isFinished = false
      AND pr.isDeleted = false
      AND pr.startDate <= :cutoffDate
    """)
	List<ProjectRoom> findProjectRoomsBefore(LocalDate cutoffDate);

	Optional<ProjectRoom> findBySessionCode(Integer sessionCode);

	boolean existsBySessionCode(int sessionCode);

	boolean existsByName(String uniqueName);


	@Query("SELECT p.name FROM ProjectRoom p "
		+ "WHERE p.name LIKE CONCAT(:baseName, '%')")
	List<String> findProjectRoomNamesStartingWith(@Param("baseName") String baseName);
}
