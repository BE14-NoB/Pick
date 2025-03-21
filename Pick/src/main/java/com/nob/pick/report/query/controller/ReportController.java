package com.nob.pick.report.query.controller;

import com.nob.pick.report.query.dto.ReportDTO;
import com.nob.pick.report.query.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 조회 관련 API 요청 처리 (DTO 반환)

@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/select")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reports = reportService.findAllReports();
       return ResponseEntity.ok(reports);
    }

    @GetMapping("/select/{memberId}")
    public ResponseEntity<List<ReportDTO>> getReportByUser(@PathVariable int memberId) {
        log.info("memberId: " + memberId);

        List<ReportDTO> reports = reportService.findReportsByMemberId(memberId);
        return ResponseEntity.ok(reports);
    }

}
