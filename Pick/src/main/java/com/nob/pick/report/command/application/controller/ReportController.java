package com.nob.pick.report.command.application.controller;

import com.nob.pick.report.command.application.dto.ReportCategory;
import com.nob.pick.report.command.application.dto.ReportDTO;
import com.nob.pick.report.command.application.dto.ReportReasonDTO;
import com.nob.pick.report.command.application.service.ReportReasonService;
import com.nob.pick.report.command.application.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CommandReportController")
@Slf4j
@RequestMapping("/command/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportReasonService reportReasonService;

    @Autowired
    public ReportController(ReportService reportService
    , ReportReasonService reportReasonService) {
        this.reportService = reportService;
        this.reportReasonService = reportReasonService;
    }

    // 신고 사유 등록
    @PostMapping("reason")
    public ResponseEntity<?> registReportReason(@RequestBody ReportReasonDTO newReportReason) {
        log.info("ReportController : registReportReason reportReasonDTO: {}", newReportReason);

        reportReasonService.registReportReason(newReportReason);
        return ResponseEntity.ok().build();
    }

    // 신고 등록
    @PostMapping("report")
    public ResponseEntity<?> registReport(@RequestBody ReportDTO newReport) {
        log.info("ReportController - regist Report : newReportDTO = {}", newReport);

        reportService.registReport(newReport);
        return ResponseEntity.ok().build();
    }

    // 신고 내역 삭제 - soft delete 처리
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReport(@PathVariable int id) {
        log.info("ReportController - delete Report : id = {}", id);

        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    // 신고 처리 상태 수정
    @PatchMapping("{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestBody ReportDTO updateReport) {
        log.info("ReportController - update Status : updateReport = {}", updateReport);

        reportService.updateReportStatus(id, updateReport);
        return ResponseEntity.ok().build();
    }

}
