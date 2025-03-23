package com.nob.pick.report.command.domain.repository;

import com.nob.pick.report.command.domain.aggregate.ReportReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportReasonRepository extends JpaRepository<ReportReason, Integer> {
    boolean existsByReason(String reason);
}
