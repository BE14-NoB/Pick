package com.nob.pick.report.command.domain.repository;

import com.nob.pick.report.command.domain.aggregate.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

}
