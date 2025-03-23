package com.nob.pick.report.command.application.service;

import com.nob.pick.report.command.application.dto.ReportDTO;
import com.nob.pick.report.command.domain.aggregate.Report;
import com.nob.pick.report.command.domain.aggregate.ReportReason;
import com.nob.pick.report.command.domain.repository.ReportReasonRepository;
import com.nob.pick.report.command.domain.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service("CommandReportService")
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportReasonRepository reportReasonRepository;
//    private final MemberRepository memberRepository;

    ReportServiceImpl(ReportRepository reportRepository
            , ReportReasonRepository reportReasonRepository
//                      , MemberRepository memberRepository
    ) {
        this.reportRepository = reportRepository;
        this.reportReasonRepository = reportReasonRepository;
//        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void registReport(ReportDTO reportDTO) {
        ReportReason reason = reportReasonRepository.findById(reportDTO.getReportReasonId())
                .orElseThrow(() -> new IllegalArgumentException("신고 사유 없음"));

//        Member member = memberRepository.findById(reportDTO.getMemberId())
//                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        String reportedAt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Report report = Report.builder()
                .reportedAt(reportedAt)
                .category(reportDTO.getCategory().getNUM())
                .reportedId(reportDTO.getReportedId())
                .reportReasonId(reportDTO.getReportReasonId())
                .memberId(reportDTO.getMemberId())
                .build();

        reportRepository.save(report);
    }


    // 신고 내역 삭제 - soft delete
    @Override
    @Transactional
    public void deleteReport(int id) {
        Report foundReport = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 신고 내역이 없습니다."));
        foundReport.markAsDeleted();
    }

    // 신고 처리 상태 수정
    @Override
    @Transactional
    public void updateReportStatus(int id, ReportDTO updateReport) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 신고 내역이 없습니다."));

        report.changeStatus(updateReport.getStatus());
    }

}
