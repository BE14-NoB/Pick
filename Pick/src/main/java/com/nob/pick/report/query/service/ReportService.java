package com.nob.pick.report.query.service;

import com.nob.pick.report.query.dao.ReportMapper;
import com.nob.pick.report.query.dto.ReportDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Controller와 DAO 사이의 비즈니스 로직 담당

@Service
public class ReportService {

    private final ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public List<ReportDTO> findAllReports() {

        return reportMapper.selectAllReports();
    }

    public List<ReportDTO> findReportsByMemberId(int memberId) {
        return reportMapper.selectReportsByMemberId(memberId);
    }
}
