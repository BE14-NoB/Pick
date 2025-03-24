package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.aggregate.MatchingEntity;
import com.nob.pick.matching.command.dto.MatchingDTO;
import com.nob.pick.matching.command.repository.MatchingRepository;
import com.nob.pick.matching.query.service.MatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class JPAMatchingServiceImpl implements JPAMatchingService {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private MatchingRepository matchingRepository;
    private MatchingService myBatisMatchingService;

    @Autowired
    public JPAMatchingServiceImpl(MatchingRepository matchingRepository, MatchingService myBatisMatchingService) {
        this.matchingRepository = matchingRepository;
        this.MyBatisMatchingService = myBatisMatchingService;
    }

    @Override
    @Transactional
    public void registMatching(MatchingDTO matchingDTO) {
        MatchingEntity registMatching = matchingDTO2MatchingEntity(matchingDTO);
        registMatching.setCreatedDateAt(formatCurrentDate());
        registMatching.setIsCompleted("N");
        matchingRepository.save(registMatching);
        resultMatchingEntity2MatchingDTO(registMatching, matchingDTO);
    }

    @Override
    @Transactional
    public void modifyMatching(MatchingDTO matchingDTO, int matchingId) {
        myBatisMatchingService.getMatchingByMatchingId(matchingId);
    }

    private void resultMatchingEntity2MatchingDTO(MatchingEntity resultMatchingEntity, MatchingDTO matchingDTO) {
        matchingDTO.setId(resultMatchingEntity.getId());
        matchingDTO.setCreatedDateAt(resultMatchingEntity.getCreatedDateAt());
        matchingDTO.setIsCompleted(resultMatchingEntity.getIsCompleted());
        matchingDTO.setLevelRange(resultMatchingEntity.getLevelRange());
        matchingDTO.setMemberId(resultMatchingEntity.getMemberId());
        matchingDTO.setTechnologyCategoryId(resultMatchingEntity.getTechnologyCategoryId());
    }

    private MatchingEntity matchingDTO2MatchingEntity(MatchingDTO matchingDTO) {
        MatchingEntity matchingEntity = new MatchingEntity();

        if(matchingDTO.getLevelRange() == 0) { matchingEntity.setLevelRange(5); }
        else { matchingEntity.setLevelRange(matchingDTO.getLevelRange()); }
        matchingEntity.setMemberId(matchingDTO.getMemberId());
        matchingEntity.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());

        return matchingEntity;
    }

    public String formatCurrentDate() {
        return FORMATTER.format(new Date());
    }
}
