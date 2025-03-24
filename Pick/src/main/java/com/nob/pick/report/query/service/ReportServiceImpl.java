package com.nob.pick.report.query.service;

import com.nob.pick.report.query.dao.ReportMapper;
import com.nob.pick.report.query.dto.ReportDTO;
import com.nob.pick.report.query.dto.enums.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Controller와 DAO 사이의 비즈니스 로직 담당

@Service("QueryReportService")
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    @Autowired
    public ReportServiceImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public List<ReportDTO> findAllReports() {

        return reportMapper.selectAllReports();
    }

    @Override
    public List<ReportDTO> findReportsByMemberId(int memberId) {
        return reportMapper.selectReportsByMemberId(memberId);
    }

    @Override
    public List<ReportDTO> findReportsByStatus(int status) {
        return reportMapper.selectReportsByStatus(status);
    }
}
