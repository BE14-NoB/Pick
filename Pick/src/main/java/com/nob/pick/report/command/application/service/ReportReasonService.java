package com.nob.pick.report.command.application.service;

import com.nob.pick.report.command.application.dto.ReportReasonDTO;
import org.springframework.stereotype.Service;

public interface ReportReasonService {
    void registReportReason(ReportReasonDTO reasonDTO);
}
