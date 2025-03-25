package com.nob.pick.regulation.command.application.controller;

import com.nob.pick.regulation.command.application.dto.RegulationDTO;
import com.nob.pick.regulation.command.application.service.RegulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController("CommandRegulationController")
@Slf4j
@RequestMapping("/command/regulation")
public class RegulationController {

    private final RegulationService regulationService;

    RegulationController(RegulationService regulationService) {
        this.regulationService = regulationService;
    }

    // 규제 등록
    @PostMapping()
    public ResponseEntity<?> regulation(@RequestBody RegulationDTO newRegulation) {
        try {
            LocalDate endDate = regulationService.registRegulation(newRegulation);

            // 날짜 포맷 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            String formattedEndDate = endDate.format(formatter);

            String message = newRegulation.getMemberId() + "번 회원의 규제가 성공적으로 등록되었습니다. 끝나는 날은 " + formattedEndDate + "입니다.";
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            String errorMessage = "규제 등록에 실패했습니다: " + e.getMessage();
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "규제 등록 중 오류가 발생했습니다."));
        }
    }

    // 규제 내역 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRegulation(@PathVariable int id) {
        try {
            regulationService.deleteRegulation(id);
            String message = id + "번 규제가 성공적으로 삭제되었습니다.";
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            String errorMessage = "규제 삭제 실패 (id=" + id + "): " + e.getMessage();
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "규제 삭제 중 오류가 발생했습니다."));
        }
    }

}
