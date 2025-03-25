package com.nob.pick.matching.command.controller;

import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.dto.CommandMatchingEntryDTO;
import com.nob.pick.matching.command.dto.CommandTechnologyCategoryDTO;
import com.nob.pick.matching.command.service.MatchingService;
import com.nob.pick.matching.command.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CommandMatchingController")
@Slf4j
public class MatchingController {

    private Environment env;
    private MatchingService matchingService;

    @Autowired
    public MatchingController(Environment env, MatchingService matchingService) {
        this.env = env;
        this.matchingService = matchingService;
    }

    // 매칭방 등록
    @PostMapping("/matching/regist")
    public ResponseEntity<ResponseMatchingVO> registMatching(@RequestBody RequestRegistMatchingVO newMatching) {
        CommandMatchingDTO matchingDTO = registMatching2MatchingDTO(newMatching);

        matchingService.registMatching(matchingDTO);

        ResponseMatchingVO successRegistMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatching);
    }

    // 매칭방 수정
    @PutMapping("/matching/modify")
    public ResponseEntity<ResponseMatchingVO> modifyMatching(@RequestBody RequestModifyMatchingVO modifyMatching) {
        CommandMatchingDTO matchingDTO = modifyMatching2MatchingDTO(modifyMatching);

        matchingService.modifyMatching(matchingDTO);

        ResponseMatchingVO successModifyMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successModifyMatching);
    }

    // 매칭방 삭제
    @DeleteMapping("/matching/delete/{matchingId}")
    public ResponseEntity<ResponseMatchingVO> deleteMatching(@PathVariable int matchingId) {
        CommandMatchingDTO matchingDTO = new CommandMatchingDTO();
        matchingDTO.setId(matchingId);

        matchingService.deleteMatching(matchingDTO);

        ResponseMatchingVO successDeleteMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successDeleteMatching);
    }

    // 해당 매칭방에 신청
    @PostMapping("/matchingEntry/regist")
    public ResponseEntity<ResponseMatchingEntryVO> registMatchingEntry(@RequestBody RequestRegistMatchingEntryVO newMatchingEntry) {
        CommandMatchingEntryDTO matchingEntryDTO = new CommandMatchingEntryDTO();
        matchingEntryDTO.setMatchingId(newMatchingEntry.getMatchingId());
        matchingEntryDTO.setMemberId(newMatchingEntry.getMemberId());
        matchingService.registMatchingEntry(matchingEntryDTO);

        ResponseMatchingEntryVO successRegistMatchingEntry = matchingEntryDTO2ResponseMatchingEntryVO(matchingEntryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatchingEntry);
    }

    // 매칭 신청 취소
    @DeleteMapping("/matchingEntry/delete/{matchingEntryId}")
    public ResponseEntity<ResponseMatchingEntryVO> deleteMatchingEntry(@PathVariable int matchingEntryId) {
        CommandMatchingEntryDTO matchingEntryDTO = new CommandMatchingEntryDTO();
        matchingEntryDTO.setId(matchingEntryId);
        matchingService.deleteMatchingEntry(matchingEntryDTO);

        ResponseMatchingEntryVO successRegistMatchingEntry = matchingEntryDTO2ResponseMatchingEntryVO(matchingEntryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatchingEntry);
    }

    // 매칭 수락
    @PostMapping("/matchingEntry/accept/{matchingEntryId}")
    public ResponseEntity<ResponseMatchingEntryVO> acceptMatchingEntry(@PathVariable int matchingEntryId) {
        CommandMatchingEntryDTO matchingEntryDTO = new CommandMatchingEntryDTO();
        matchingEntryDTO.setId(matchingEntryId);
        matchingService.acceptMatchingEntry(matchingEntryDTO);

        ResponseMatchingEntryVO successRegistMatchingEntry = matchingEntryDTO2ResponseMatchingEntryVO(matchingEntryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatchingEntry);
    }

    // 기술 카테고리 등록
    @PostMapping("/technologyCategory/regist")
    public ResponseEntity<ResponseTechnologyCategoryVO> registTechnologyCategory(@RequestBody RequestRegistTechnologyCategoryVO newTechnologyCategory) {
        CommandTechnologyCategoryDTO technologyCategoryDTO = registTechnologyCategory2TechnologyCategoryDTO(newTechnologyCategory);

        matchingService.registTechnologyCategory(technologyCategoryDTO);

        ResponseTechnologyCategoryVO successRegistTechnologyCategory = technologyCategoryDTO2ResponseTechnologyCategory(technologyCategoryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistTechnologyCategory);
    }

    // 기술 카테고리 수정
    @PutMapping("/technologyCategory/modify")
    public ResponseEntity<ResponseTechnologyCategoryVO> modifyTechnologyCategory(@RequestBody RequestModifyTechnologyCategoryVO modifyTechnologyCategory) {
        CommandTechnologyCategoryDTO technologyCategoryDTO = modifyTechnologyCategory2TechnologyCategoryDTO(modifyTechnologyCategory);

        matchingService.modifyTechnologyCategory(technologyCategoryDTO);

        ResponseTechnologyCategoryVO successModifyTechnologyCategory = technologyCategoryDTO2ResponseTechnologyCategory(technologyCategoryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successModifyTechnologyCategory);
    }

    // 기술 카테고리 삭제
    @DeleteMapping("/technologyCategory/delete/{technologyCategoryId}")
    public ResponseEntity<ResponseTechnologyCategoryVO> deleteTechnologyCategory(@PathVariable int technologyCategoryId) {
        CommandTechnologyCategoryDTO technologyCategoryDTO = new CommandTechnologyCategoryDTO();
        technologyCategoryDTO.setId(technologyCategoryId);
        matchingService.deleteTechnologyCategory(technologyCategoryDTO);
        ResponseTechnologyCategoryVO successDeleteTechnologyCategory = technologyCategoryDTO2ResponseTechnologyCategory(technologyCategoryDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successDeleteTechnologyCategory);
    }

    private CommandTechnologyCategoryDTO modifyTechnologyCategory2TechnologyCategoryDTO(RequestModifyTechnologyCategoryVO modifyTechnologyCategory) {
        CommandTechnologyCategoryDTO technologyCategoryDTO = new CommandTechnologyCategoryDTO();

        technologyCategoryDTO.setId(modifyTechnologyCategory.getId());
        technologyCategoryDTO.setName(modifyTechnologyCategory.getName());
        technologyCategoryDTO.setRefTechnologyCategoryId(modifyTechnologyCategory.getRefTechnologyCategoryId());

        return technologyCategoryDTO;
    }


    private ResponseTechnologyCategoryVO technologyCategoryDTO2ResponseTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO) {
        ResponseTechnologyCategoryVO responseTechnologyCategory = new ResponseTechnologyCategoryVO();
        responseTechnologyCategory.setId(technologyCategoryDTO.getId());
        responseTechnologyCategory.setName(technologyCategoryDTO.getName());
        responseTechnologyCategory.setIsDeleted(technologyCategoryDTO.getIsDeleted());
        responseTechnologyCategory.setRefTechnologyCategoryId(technologyCategoryDTO.getRefTechnologyCategoryId());
        return responseTechnologyCategory;
    }

    private CommandTechnologyCategoryDTO registTechnologyCategory2TechnologyCategoryDTO(RequestRegistTechnologyCategoryVO newTechnologyCategory) {
        CommandTechnologyCategoryDTO technologyCategoryDTO = new CommandTechnologyCategoryDTO();

        technologyCategoryDTO.setName(newTechnologyCategory.getName());
        technologyCategoryDTO.setRefTechnologyCategoryId(newTechnologyCategory.getRefTechnologyCategoryId());

        return technologyCategoryDTO;
    }

    private ResponseMatchingEntryVO matchingEntryDTO2ResponseMatchingEntryVO(CommandMatchingEntryDTO matchingEntryDTO) {
        ResponseMatchingEntryVO responseMatchingEntry = new ResponseMatchingEntryVO();
        responseMatchingEntry.setId(matchingEntryDTO.getId());
        responseMatchingEntry.setAppliedDateAt(matchingEntryDTO.getAppliedDateAt());
        responseMatchingEntry.setIsAccepted(matchingEntryDTO.getIsAccepted());
        responseMatchingEntry.setIsCanceled(matchingEntryDTO.getIsCanceled());
        responseMatchingEntry.setMatchingId(matchingEntryDTO.getMatchingId());
        responseMatchingEntry.setMemberId(matchingEntryDTO.getMemberId());
        return responseMatchingEntry;
    }

    private ResponseMatchingVO matchingDTO2ResponseMatchingVO(CommandMatchingDTO matchingDTO) {
        ResponseMatchingVO responseMatching = new ResponseMatchingVO();
        responseMatching.setId(matchingDTO.getId());
        responseMatching.setCreatedDateAt(matchingDTO.getCreatedDateAt());
        responseMatching.setIsCompleted(matchingDTO.getIsCompleted());
        responseMatching.setIsDeleted(matchingDTO.getIsDeleted());
        responseMatching.setMaximumParticipant(matchingDTO.getMaximumParticipant());
        responseMatching.setCurrentParticipant(matchingDTO.getCurrentParticipant());
        responseMatching.setLevelRange(matchingDTO.getLevelRange());
        responseMatching.setMemberId(matchingDTO.getMemberId());
        responseMatching.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());

        return responseMatching;
    }

    private CommandMatchingDTO modifyMatching2MatchingDTO(RequestModifyMatchingVO modifyMatching) {
        CommandMatchingDTO matchingDTO = new CommandMatchingDTO();

        matchingDTO.setId(modifyMatching.getId());
        if(modifyMatching.getMaximumParticipant() != null) { matchingDTO.setMaximumParticipant(modifyMatching.getMaximumParticipant()); }
        if(modifyMatching.getIsCompleted() != null) { matchingDTO.setIsCompleted(modifyMatching.getIsCompleted()); }
        if(modifyMatching.getLevelRange() != null) { matchingDTO.setLevelRange(modifyMatching.getLevelRange()); }
        if(modifyMatching.getTechnologyCategoryId() !=null) { matchingDTO.setTechnologyCategoryId(modifyMatching.getTechnologyCategoryId()); }

        return matchingDTO;
    }

    private CommandMatchingDTO registMatching2MatchingDTO(RequestRegistMatchingVO newMatching) {
        CommandMatchingDTO matchingDTO = new CommandMatchingDTO();

        matchingDTO.setMaximumParticipant(newMatching.getMaximumParticipant());
        matchingDTO.setLevelRange(newMatching.getLevelRange());
        matchingDTO.setMemberId(newMatching.getMemberId());
        matchingDTO.setTechnologyCategoryId(newMatching.getTechnologyCategoryId());

        return matchingDTO;
    }


}
