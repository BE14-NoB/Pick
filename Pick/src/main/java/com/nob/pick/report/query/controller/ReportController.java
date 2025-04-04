package com.nob.pick.report.query.controller;

import com.nob.pick.report.query.dto.ReportDTO;
import com.nob.pick.report.query.dto.enums.ReportStatus;
import com.nob.pick.report.query.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

// 조회 관련 API 요청 처리 (DTO 반환)

@RestController("QueryReportController")
@Slf4j
@RequestMapping("/query/report")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 전체 신고 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reports = reportService.findAllReports();
       return ResponseEntity.ok(reports);
    }

    // 특정 회원이 받은 신고 내역 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ReportDTO>> getReportsByMemberId(@PathVariable int memberId) {
        log.info("memberId: " + memberId);

        List<ReportDTO> reports = reportService.findReportsByMemberId(memberId);
        return ResponseEntity.ok(reports);
    }

    // 상태별 신고 목록 조회
    @GetMapping("/status/{statusNum}")
    public ResponseEntity<?> getReportByStatus(@PathVariable int statusNum) {
        log.info("statusNum: " + statusNum);

        // 코드가 없는 경우 예외 처리
        // (프론트에서 선택을 enum에 있는 값으로밖에 선택을 못하게 막을 예정이긴 함)
        if (Arrays.stream(ReportStatus.values()).noneMatch(s -> s.getNUM() == statusNum)) {
            String errorMessage = "존재하지 않는 상태 코드입니다 : " + statusNum;
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        List<ReportDTO> reports = reportService.findReportsByStatus(statusNum);
        return ResponseEntity.ok(reports);
    }

    // 삭제 여부에 따른 신고 목록 조회
    @GetMapping("/deletion/{deletion}")
    public ResponseEntity<?> getDeletedReports(@PathVariable String deletion) {
        log.info("deleted: " + deletion);

        // 'Y'나 'N'이 아닌 경우
        if(!(deletion.equalsIgnoreCase("Y") || deletion.equalsIgnoreCase("N"))) {
            String errorMessage = "존재하지 않는 삭제 여부입니다. : " + deletion;
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        // "Y"나 "y"이면 true, "N"이거나 "n"이면 false
        boolean isDeleted = deletion.equalsIgnoreCase("Y");
        List<ReportDTO> reports = reportService.findReportsByDeletion(isDeleted);
        return ResponseEntity.ok(reports);
    }

}
