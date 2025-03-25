package com.nob.pick.regulation.command.domain.repository;

import com.nob.pick.regulation.command.domain.aggregate.Regulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulationRepository extends JpaRepository<Regulation, Integer> {
}
