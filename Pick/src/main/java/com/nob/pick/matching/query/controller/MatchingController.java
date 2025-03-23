package com.nob.pick.matching.query.controller;

import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.dto.MatchingEntryDTO;
import com.nob.pick.matching.query.dto.TechnologyCategoryDTO;
import com.nob.pick.matching.query.service.MatchingService;
import com.nob.pick.matching.query.vo.ResponseMatchingEntryVO;
import com.nob.pick.matching.query.vo.ResponseMatchingVO;
import com.nob.pick.matching.query.vo.ResponseTechnologyCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class MatchingController {

    private final MatchingService matchingService;

    @Autowired
    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    // 전체 매칭방 조회
    @GetMapping("/matching/all")
    public ResponseEntity<List<ResponseMatchingVO>> findAllMatching() {

        List<MatchingDTO> matchingDTOList = matchingService.getMatching();

        List<ResponseMatchingVO> returnValue = matchingDTO2ResponseMatching(matchingDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 매칭방 id로 조회
    @GetMapping("/matching/{matchingId}")
    public ResponseEntity<List<ResponseMatchingVO>> findMatchingByMatchingId(@PathVariable int matchingId) {

        List<MatchingDTO> matchingDTOList = matchingService.getMatchingByMatchingId(matchingId);

        List<ResponseMatchingVO> returnValue = matchingDTO2ResponseMatching(matchingDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 카테고리 id로 매칭방 조회
    @GetMapping("/matching/technologyCategories/{technologyCategoryId}")
    public ResponseEntity<List<ResponseMatchingVO>> findMatchingByTechnologyCategoryId(@PathVariable int technologyCategoryId) {

        List<MatchingDTO> matchingDTOList = matchingService.getMatchingByTechnologyCategoryId(technologyCategoryId);

        List<ResponseMatchingVO> returnValue = matchingDTO2ResponseMatching(matchingDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 매칭방 id로 신청자 조회
    @GetMapping("/matchingEntry/matching/{matchingId}")
    public ResponseEntity<List<ResponseMatchingEntryVO>> findMatchingEntryByMatchingId(@PathVariable int matchingId) {

        List<MatchingEntryDTO> matchingEntryDTOList = matchingService.getMatchingEntryByMatchingId(matchingId);

        List<ResponseMatchingEntryVO> returnValue = matchingEntryDTO2ResponseMatchingEntry(matchingEntryDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 전체 기술 카테고리 조회
    @GetMapping("/technologyCategory/all")
    public ResponseEntity<List<ResponseTechnologyCategoryVO>> findAllTechnologyCategory() {

        List<TechnologyCategoryDTO> technologyCategoryDTOList = matchingService.getTechnologyCategory();

        List<ResponseTechnologyCategoryVO> returnValue = technologyCategoryDTO2ResponseTechnology(technologyCategoryDTOList);

        log.info("controller" + returnValue);
        return ResponseEntity.ok().body(returnValue);
    }

    // 카테고리 id로 조회
    @GetMapping("/technologyCategory/{technologyCategoryId}")
    public ResponseEntity<List<ResponseTechnologyCategoryVO>> findTechnologyCategoryByTechnologyCategoryId(@PathVariable int technologyCategoryId) {

        List<TechnologyCategoryDTO> technologyCategoryDTOList = matchingService.getTechnologyCategoryByTechnologyCategoryId(technologyCategoryId);

        List<ResponseTechnologyCategoryVO> returnValue = technologyCategoryDTO2ResponseTechnology(technologyCategoryDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 카테고리 id로 하위 카테고리 조회
    @GetMapping("/technologyCategory/subTechnologyCategory/{refTechnologyCategoryId}")
    public ResponseEntity<List<ResponseTechnologyCategoryVO>> findSubTechnologyCategoryByRefTechnologyCategoryId(@PathVariable int refTechnologyCategoryId) {

        List<TechnologyCategoryDTO> technologyCategoryDTOList = matchingService.getSubTechnologyCategoryByRefTechnologyCategoryId(refTechnologyCategoryId);

        List<ResponseTechnologyCategoryVO> returnValue = technologyCategoryDTO2ResponseTechnology(technologyCategoryDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    // 상위 카테고리만 조회
    @GetMapping("/technologyCategory/parentTechnologyCategory")
    public ResponseEntity<List<ResponseTechnologyCategoryVO>> findParentTechnologyCategory() {

        List<TechnologyCategoryDTO> technologyCategoryDTOList = matchingService.getParentTechnologyCategory();

        List<ResponseTechnologyCategoryVO> returnValue = technologyCategoryDTO2ResponseTechnology(technologyCategoryDTOList);

        return ResponseEntity.ok().body(returnValue);
    }

    private List<ResponseMatchingEntryVO> matchingEntryDTO2ResponseMatchingEntry(List<MatchingEntryDTO> matchingEntryDTOList) {

        List<ResponseMatchingEntryVO> responseList = new ArrayList<>();

        for(MatchingEntryDTO matchingEntryDTO : matchingEntryDTOList) {
            ResponseMatchingEntryVO responseMatchingEntry = new ResponseMatchingEntryVO();
            responseMatchingEntry.setId(matchingEntryDTO.getId());
            responseMatchingEntry.setMemberId(matchingEntryDTO.getMemberId());
            responseMatchingEntry.setMatchingId(matchingEntryDTO.getMatchingId());

            responseList.add(responseMatchingEntry);
        }

        return responseList;
    }

    private List<ResponseTechnologyCategoryVO> technologyCategoryDTO2ResponseTechnology(List<TechnologyCategoryDTO> technologyCategoryDTOList) {

        List<ResponseTechnologyCategoryVO> responseList = new ArrayList<>();

        for(TechnologyCategoryDTO technologyCategoryDTO : technologyCategoryDTOList) {
            ResponseTechnologyCategoryVO responseTechnologyCategory = new ResponseTechnologyCategoryVO();
            responseTechnologyCategory.setId(technologyCategoryDTO.getId());
            responseTechnologyCategory.setName(technologyCategoryDTO.getName());
            responseTechnologyCategory.setRefTechnologyCategoryId(technologyCategoryDTO.getRefTechnologyCategoryId());

            responseList.add(responseTechnologyCategory);
        }

        return responseList;
    }

    private List<ResponseMatchingVO> matchingDTO2ResponseMatching(List<MatchingDTO> matchingDTOList) {

        List<ResponseMatchingVO> responseList = new ArrayList<>();

        for (MatchingDTO matchingDTO : matchingDTOList) {
            ResponseMatchingVO responseMatching = new ResponseMatchingVO();
            responseMatching.setId(matchingDTO.getId());
            responseMatching.setMemberId(matchingDTO.getMemberId());
            responseMatching.setLevelRange(matchingDTO.getLevelRange());
            responseMatching.setTechnologyCategories(matchingDTO.getTechnologyCategories());

            responseList.add(responseMatching);
        }

        return responseList;
    }
}
