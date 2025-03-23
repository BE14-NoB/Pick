package com.nob.pick.report.command.application.service;

import com.nob.pick.report.command.application.dto.ReportDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


// 비즈니스 로직의 흐름 관리(트랜잭션 등)

public interface ReportService {

    void registReport(ReportDTO reportDTO);

    void deleteReport(int id);

    void updateReportStatus(int id, ReportDTO updateReport);
}
