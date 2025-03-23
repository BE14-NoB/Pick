package com.nob.pick.report.command.application.controller;

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

    @PostMapping("reason")
    public ResponseEntity<?> registReportReason(@RequestBody ReportReasonDTO reportReasonDTO) {
        log.info("ReportController : registReportReason reportReasonDTO: {}", reportReasonDTO);

        reportReasonService.registReportReason(reportReasonDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report")
    public void registReportPage() {}

    @PostMapping("report")
    public ResponseEntity<?> registReport(@RequestBody ReportDTO newReportDTO) {
        log.info("ReportController - regist Report : newReportDTO = {}", newReportDTO);

        reportService.registReport(newReportDTO);
        return ResponseEntity.ok().build();
    }

}
