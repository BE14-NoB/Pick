package com.nob.pick.matching.command.controller;

import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.service.MatchingService;
import com.nob.pick.matching.command.vo.RequestModifyMatchingVO;
import com.nob.pick.matching.command.vo.RequestRegistMatchingVO;
import com.nob.pick.matching.command.vo.ResponseMatchingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/matching/regist")
    public ResponseEntity<ResponseMatchingVO> registMatching(@RequestBody RequestRegistMatchingVO newMatching) {
        log.info("request matching: {}", newMatching);
        CommandMatchingDTO matchingDTO = registMatching2MatchingDTO(newMatching);
        log.info("request2matchingDTO: {}", matchingDTO);

        matchingService.registMatching(matchingDTO);

        ResponseMatchingVO successRegistMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successRegistMatching);
    }

    @PostMapping("/matching/modify")
    public ResponseEntity<ResponseMatchingVO> modifyMatching(@RequestBody RequestModifyMatchingVO modifyMatching) {
        CommandMatchingDTO matchingDTO = modifyMatching2MatchingDTO(modifyMatching);

        matchingService.modifyMatching(matchingDTO);

        ResponseMatchingVO successModifyMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successModifyMatching);
    }

    @PostMapping("/matching/delete/{matchingId}")
    public ResponseEntity<ResponseMatchingVO> deleteMatching(@PathVariable int matchingId) {
        CommandMatchingDTO matchingDTO = new CommandMatchingDTO();
        matchingDTO.setId(matchingId);

        matchingService.deleteMatching(matchingDTO);

        ResponseMatchingVO successDeleteMatching = matchingDTO2ResponseMatchingVO(matchingDTO);

        return ResponseEntity.status(HttpStatus.OK).body(successDeleteMatching);
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
