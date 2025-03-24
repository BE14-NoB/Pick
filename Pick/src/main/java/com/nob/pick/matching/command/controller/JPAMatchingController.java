package com.nob.pick.matching.command.controller;

import com.nob.pick.matching.command.dto.MatchingDTO;
import com.nob.pick.matching.command.service.JPAMatchingService;
import com.nob.pick.matching.command.vo.RequestModifyMatchingVO;
import com.nob.pick.matching.command.vo.RequestRegistMatchingVO;
import com.nob.pick.matching.command.vo.ResponseModifyMatchingVO;
import com.nob.pick.matching.command.vo.ResponseRegistMatchingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JPAMatchingController {

    private Environment env;
    private JPAMatchingService jpaMatchingService;

    @Autowired
    public JPAMatchingController(Environment env, JPAMatchingService jpaMatchingService) {
        this.env = env;
        this.jpaMatchingService = jpaMatchingService;
    }

    // 매칭 등록하기 -> 매칭 완료조건이 인원 수 다 찾을때? 어케하지? 수정해야하면 나중에 수정하자
    @PostMapping("/matching/regist")
    public ResponseEntity<ResponseRegistMatchingVO> registMatching(@RequestBody RequestRegistMatchingVO newMatching) {
        MatchingDTO matchingDTO = registMatching2MatchingDTO(newMatching);

        jpaMatchingService.registMatching(matchingDTO);

        ResponseRegistMatchingVO successRegistMatching = matchingDTO2ResponseRegistMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatching);
    }

    @PostMapping("matching/modify/{matchingId}")
    public ResponseEntity<ResponseModifyMatchingVO> modifyMatching(@RequestBody RequestModifyMatchingVO modifyMatching, @PathVariable int matchingId) {
        MatchingDTO matchingDTO = modifyMatching2MatchingDTO(modifyMatching);

        jpaMatchingService.modifyMatching(matchingDTO, matchingId);

        ResponseModifyMatchingVO successModifyMatching = matchingDTO2ResponseModifyMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successModifyMatching);
    }

    private ResponseModifyMatchingVO matchingDTO2ResponseModifyMatchingVO(MatchingDTO matchingDTO) {
        ResponseModifyMatchingVO responseMatching = new ResponseModifyMatchingVO();

        responseMatching.setId(matchingDTO.getId());
        responseMatching.setCreatedDateAt(matchingDTO.getCreatedDateAt());
        responseMatching.setIsCompleted(matchingDTO.getIsCompleted());
        responseMatching.setLevelRange(matchingDTO.getLevelRange());
        responseMatching.setMemberId(matchingDTO.getMemberId());
        responseMatching.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());

        return responseMatching;
    }


    private ResponseRegistMatchingVO matchingDTO2ResponseRegistMatchingVO(MatchingDTO matchingDTO) {
        ResponseRegistMatchingVO responseMatching = new ResponseRegistMatchingVO();
        responseMatching.setId(matchingDTO.getId());
        responseMatching.setCreatedDateAt(matchingDTO.getCreatedDateAt());
        responseMatching.setIsCompleted(matchingDTO.getIsCompleted());
        responseMatching.setLevelRange(matchingDTO.getLevelRange());
        responseMatching.setMemberId(matchingDTO.getMemberId());
        responseMatching.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());

        return responseMatching;
    }

    private MatchingDTO modifyMatching2MatchingDTO(RequestModifyMatchingVO modifyMatching) {
        MatchingDTO matchingDTO = new MatchingDTO();

        matchingDTO.setIsCompleted(modifyMatching.getIsCompleted());
        matchingDTO.setLevelRange(modifyMatching.getLevelRange());
        matchingDTO.setMemberId(modifyMatching.getMemberId());
        matchingDTO.setTechnologyCategoryId(modifyMatching.getTechnologyCategoryId());

        return matchingDTO;
    }

    private MatchingDTO registMatching2MatchingDTO(RequestRegistMatchingVO newMatching) {
        MatchingDTO matchingDTO = new MatchingDTO();

        matchingDTO.setLevelRange(newMatching.getLevelRange());
        matchingDTO.setMemberId(newMatching.getMemberId());
        matchingDTO.setTechnologyCategoryId(newMatching.getTechnologyCategoryId());

        return matchingDTO;
    }


}
