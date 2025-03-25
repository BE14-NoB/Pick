package com.nob.pick.report.command.application.service;

import com.nob.pick.report.command.application.dto.ReportReasonDTO;
import com.nob.pick.report.command.domain.aggregate.ReportReason;
import com.nob.pick.report.command.domain.repository.ReportReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ReportReasonService")
public class ReportReasonServiceImpl implements ReportReasonService{

    private final ReportReasonRepository reportReasonRepository;

    @Autowired
    ReportReasonServiceImpl(ReportReasonRepository reportReasonRepository) {
        this.reportReasonRepository = reportReasonRepository;
    }

    @Override
    public void registReportReason(ReportReasonDTO reportReasonDTO) {

        // 중복 방지
        if(reportReasonRepository.existsByReason(reportReasonDTO.getReason())) {
            throw new IllegalArgumentException("이미 존재하는 신고 사유입니다.");
        }

        ReportReason reason = ReportReason.builder()
                .reason(reportReasonDTO.getReason())
                .build();

        reportReasonRepository.save(reason);
    }
}
