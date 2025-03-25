package com.nob.pick.report.command.application.controller;

import com.nob.pick.report.command.application.dto.ReportCategory;
import com.nob.pick.report.command.application.dto.ReportDTO;
import com.nob.pick.report.command.application.dto.ReportReasonDTO;
import com.nob.pick.report.command.application.service.ReportReasonService;
import com.nob.pick.report.command.application.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        try {
            reportReasonService.registReportReason(newReportReason);
            return ResponseEntity.ok("사유 : " + newReportReason.getReason() + "가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("신고 사유 등록 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 사유 등록에 실패했습니다.");
        }
    }

    // 신고 등록
    @PostMapping("report")
    public ResponseEntity<?> registReport(@RequestBody ReportDTO newReport) {
        log.info("ReportController - regist Report : newReportDTO = {}", newReport);

        try {
            reportService.registReport(newReport);
            return ResponseEntity.ok(newReport.getReportedId() + "번 " + newReport.getCategory()+ "의 신고가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("신고 등록 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 등록에 실패했습니다.");
        }
    }

    // 신고 내역 삭제 - soft delete 처리
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReport(@PathVariable int id) {
        log.info("ReportController - delete Report : id = {}", id);

        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok(id + "번 신고가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.error("신고 삭제 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 삭제에 실패했습니다.");
        }
    }

    // 신고 처리 상태 수정
    @PatchMapping("{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestBody ReportDTO updateReport) {
        log.info("ReportController - update Status : updateReport = {}", updateReport);

        try {
            reportService.updateReportStatus(id, updateReport);
            return ResponseEntity.ok(id + "번의 신고 상태가 " + updateReport.getStatus() + "으로 변경되었습니다.");
        } catch (Exception e) {
            log.error("신고 상태 변경 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 상태 변경에 실패했습니다.");
        }
    }

}
