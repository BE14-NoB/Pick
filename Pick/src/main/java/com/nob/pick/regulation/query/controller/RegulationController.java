package com.nob.pick.regulation.query.controller;

import com.nob.pick.regulation.query.dto.RegulationDTO;
import com.nob.pick.regulation.query.service.RegulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("QueryRegulationController")
@Slf4j
@RequestMapping("query/regulation")
public class RegulationController {

    private RegulationService regulationService;

    @Autowired
    public RegulationController(RegulationService regulationService) {
        this.regulationService = regulationService;
    }

    // 모든 규제 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<RegulationDTO>> getAllRegulations() {
        List<RegulationDTO> regulations = regulationService.findAllRegulations();
        return ResponseEntity.ok(regulations);

    }

    // 특정 회원 규제 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<RegulationDTO>> getRegulationByMember(@PathVariable int memberId) {
        List<RegulationDTO> regulations = regulationService.findRegulationByMemberId(memberId);
        return ResponseEntity.ok(regulations);
    }
    
    // 삭제 여부별 규제 목록 조회
    @GetMapping("/deletion/{deletion}")
    public ResponseEntity<?> deleteRegulation(@PathVariable String deletion) {
        log.info("deleted: " + deletion);

        // 'Y'나 'N'이 아닌 경우
        if(!(deletion.equalsIgnoreCase("Y") || deletion.equalsIgnoreCase("N"))) {
            String errorMessage = "존재하지 않는 삭제 여부입니다. : " + deletion;
            log.error(errorMessage);
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        // "Y"나 "y"이면 true, "N"이거나 "n"이면 false
        boolean isDeleted = deletion.equalsIgnoreCase("Y");
        List<RegulationDTO> regulations = regulationService.findRegulationsByDeletion(isDeleted);
        return ResponseEntity.ok(regulations);
    }

}
