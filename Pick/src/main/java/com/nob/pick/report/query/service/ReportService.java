package com.nob.pick.report.query.service;

import com.nob.pick.report.query.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public interface ReportService {
    List<ReportDTO> findAllReports();
    List<ReportDTO> findReportsByMemberId(int memberId);
}
