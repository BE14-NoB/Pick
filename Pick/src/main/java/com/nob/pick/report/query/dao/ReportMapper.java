package com.nob.pick.report.query.dao;

import com.nob.pick.report.query.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// DB에 직접 접근하는 Mapper 클래스

@Mapper
public interface ReportMapper {
    List<ReportDTO> selectAllReports();
    List<ReportDTO> selectReportsByMemberId(int memberId);

    List<ReportDTO> selectReportsByStatus(int status);

    List<ReportDTO> selectReportsByDeletion(boolean isDeleted);
}
