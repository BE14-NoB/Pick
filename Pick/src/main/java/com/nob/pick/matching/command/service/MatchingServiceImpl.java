package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.aggregate.MatchingEntity;
import com.nob.pick.matching.command.aggregate.MatchingEntryEntity;
import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.dto.RegistMatchingEntryDTO;
import com.nob.pick.matching.command.repository.MatchingEntryRepository;
import com.nob.pick.matching.command.repository.MatchingRepository;
import com.nob.pick.matching.command.repository.TechnologyCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service("CommandMatchingService")
@Slf4j
public class MatchingServiceImpl implements MatchingService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private MatchingRepository matchingRepository;
    private MatchingEntryRepository matchingEntryRepository;
    private TechnologyCategoryRepository technologyCategoryRepository;

    @Autowired
    public MatchingServiceImpl(MatchingRepository matchingRepository, MatchingEntryRepository matchingEntryRepository, TechnologyCategoryRepository technologyCategoryRepository) {
        this.matchingRepository = matchingRepository;
        this.matchingEntryRepository = matchingEntryRepository;
        this.technologyCategoryRepository = technologyCategoryRepository;
    }

    @Override
    @Transactional
    public void registMatching(CommandMatchingDTO matchingDTO) {
        MatchingEntity registMatching = matchingDTO2MatchingEntity(matchingDTO);
        registMatching.setCreatedDateAt(formatCurrentDateTime());
        registMatching.setIsCompleted("N");
        registMatching.setIsDeleted("N");
        matchingRepository.save(registMatching);
        resultMatchingEntity2MatchingDTO(registMatching, matchingDTO);
    }

    @Override
    @Transactional
    public void modifyMatching(CommandMatchingDTO matchingDTO) {
        MatchingEntity findMatching = matchingRepository.findById(matchingDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntity not found"));

        if(matchingDTO.getLevelRange() != 0) {
            findMatching.setLevelRange(matchingDTO.getLevelRange());
        }
        if(matchingDTO.getIsCompleted() != null) {
            findMatching.setIsCompleted(matchingDTO.getIsCompleted());
        }
        if(matchingDTO.getMaximumParticipant() != 0) {
            findMatching.setMaximumParticipant(matchingDTO.getMaximumParticipant());
        }
        if(matchingDTO.getTechnologyCategoryId() != 0) {
            findMatching.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());
        }

        matchingRepository.save(findMatching);
        resultMatchingEntity2MatchingDTO(findMatching, matchingDTO);
    }

    @Override
    @Transactional
    public void deleteMatching(CommandMatchingDTO matchingDTO) {
        MatchingEntity findMatching = matchingRepository.findById(matchingDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntity not found"));

        findMatching.setIsDeleted("Y");
        matchingRepository.save(findMatching);
        resultMatchingEntity2MatchingDTO(findMatching, matchingDTO);
    }

    @Override
    public void registMatchingEntry(RegistMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity registMatchingEntry = matchingEntryDTO2MatchingEntryEntity(matchingEntryDTO);
        registMatchingEntry.setAppliedDateAt(formatCurrentDateTime());
        registMatchingEntry.setIsCanceled("N");
        registMatchingEntry.setIsAccepted("N");
        matchingEntryRepository.save(registMatchingEntry);
        resultMatchingEntryEntity2MatchingDTO(registMatchingEntry, matchingEntryDTO);
    }

    @Override
    public void deleteMatchingEntry(RegistMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity findMatchingEntry = matchingEntryRepository.findById(matchingEntryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntryEntity not found"));

        findMatchingEntry.setIsCanceled("Y");
        matchingEntryRepository.save(findMatchingEntry);
        resultMatchingEntryEntity2MatchingDTO(findMatchingEntry, matchingEntryDTO);
    }

    private void resultMatchingEntryEntity2MatchingDTO(MatchingEntryEntity registMatchingEntry, RegistMatchingEntryDTO matchingEntryDTO) {
        matchingEntryDTO.setId(registMatchingEntry.getId());
        matchingEntryDTO.setMemberId(registMatchingEntry.getMemberId());
        matchingEntryDTO.setMatchingId(registMatchingEntry.getMatchingId());
        matchingEntryDTO.setAppliedDateAt(registMatchingEntry.getAppliedDateAt());
    }

    private MatchingEntryEntity matchingEntryDTO2MatchingEntryEntity(RegistMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity matchingEntryEntity = new MatchingEntryEntity();
        matchingEntryEntity.setMemberId(matchingEntryDTO.getMemberId());
        matchingEntryEntity.setMatchingId(matchingEntryDTO.getMatchingId());
        return matchingEntryEntity;
    }

    private void resultMatchingEntity2MatchingDTO(MatchingEntity resultMatchingEntity, CommandMatchingDTO matchingDTO) {
        matchingDTO.setId(resultMatchingEntity.getId());
        matchingDTO.setCreatedDateAt(resultMatchingEntity.getCreatedDateAt());
        matchingDTO.setIsCompleted(resultMatchingEntity.getIsCompleted());
        matchingDTO.setIsDeleted(resultMatchingEntity.getIsDeleted());
        matchingDTO.setMaximumParticipant(resultMatchingEntity.getMaximumParticipant());
        matchingDTO.setCurrentParticipant(resultMatchingEntity.getCurrentParticipant());
        matchingDTO.setLevelRange(resultMatchingEntity.getLevelRange());
        matchingDTO.setMemberId(resultMatchingEntity.getMemberId());
        matchingDTO.setTechnologyCategoryId(resultMatchingEntity.getTechnologyCategoryId());
    }

    private MatchingEntity matchingDTO2MatchingEntity(CommandMatchingDTO matchingDTO) {
        MatchingEntity matchingEntity = new MatchingEntity();

        if(matchingDTO.getLevelRange() != 0) {
            matchingEntity.setLevelRange(matchingDTO.getLevelRange());
        } else {
            matchingEntity.setLevelRange(5);
        }
        if(matchingDTO.getMaximumParticipant() != 0) {
            matchingEntity.setMaximumParticipant(matchingDTO.getMaximumParticipant());
        } else {
            matchingEntity.setMaximumParticipant(5);
        }
        if(matchingDTO.getCurrentParticipant() != 0) {
            matchingEntity.setCurrentParticipant(matchingDTO.getCurrentParticipant());
        } else {
            matchingEntity.setCurrentParticipant(1);
        }

        matchingEntity.setMemberId(matchingDTO.getMemberId());
        matchingEntity.setTechnologyCategoryId(matchingDTO.getTechnologyCategoryId());

        return matchingEntity;
    }

    public String formatCurrentDateTime() {
        return LocalDateTime.now().format(formatter);
    }
}
