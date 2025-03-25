package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.aggregate.MatchingEntity;
import com.nob.pick.matching.command.aggregate.MatchingEntryEntity;
import com.nob.pick.matching.command.aggregate.TechnologyCategoryEntity;
import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.dto.CommandMatchingEntryDTO;
import com.nob.pick.matching.command.dto.CommandTechnologyCategoryDTO;
import com.nob.pick.matching.command.repository.MatchingEntryRepository;
import com.nob.pick.matching.command.repository.MatchingRepository;
import com.nob.pick.matching.command.repository.TechnologyCategoryRepository;
import com.nob.pick.matching.query.dto.MatchingEntryDTO;
import com.nob.pick.project.command.application.dto.RequestParticipantDTO;
import com.nob.pick.project.command.application.dto.RequestProjectRoomDTO;
import com.nob.pick.project.command.application.service.ProjectRoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("CommandMatchingService")
@Slf4j
public class MatchingServiceImpl implements MatchingService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final MatchingRepository matchingRepository;
    private final MatchingEntryRepository matchingEntryRepository;
    private final TechnologyCategoryRepository technologyCategoryRepository;
    private final ProjectRoomService projectRoomService;
    private final com.nob.pick.matching.query.service.MatchingService queryService;


    @Autowired
    public MatchingServiceImpl(MatchingRepository matchingRepository,
                               MatchingEntryRepository matchingEntryRepository,
                               TechnologyCategoryRepository technologyCategoryRepository,
                               ProjectRoomService projectRoomService,
                               com.nob.pick.matching.query.service.MatchingService queryService) {
        this.matchingRepository = matchingRepository;
        this.matchingEntryRepository = matchingEntryRepository;
        this.technologyCategoryRepository = technologyCategoryRepository;
        this.projectRoomService = projectRoomService;
        this.queryService = queryService;
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
    @Transactional
    public void registMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity registMatchingEntry = matchingEntryDTO2MatchingEntryEntity(matchingEntryDTO);
        registMatchingEntry.setAppliedDateAt(formatCurrentDateTime());
        registMatchingEntry.setIsCanceled("N");
        registMatchingEntry.setIsAccepted("N");
        matchingEntryRepository.save(registMatchingEntry);
        resultMatchingEntryEntity2MatchingDTO(registMatchingEntry, matchingEntryDTO);
    }

    @Override
    @Transactional
    public void deleteMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity findMatchingEntry = matchingEntryRepository.findById(matchingEntryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntryEntity not found"));

        findMatchingEntry.setIsCanceled("Y");
        matchingEntryRepository.save(findMatchingEntry);
        resultMatchingEntryEntity2MatchingDTO(findMatchingEntry, matchingEntryDTO);
    }

    @Override
    @Transactional
    public void acceptMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO) {
        MatchingEntryEntity findMatchingEntry = matchingEntryRepository.findById(matchingEntryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntryEntity not found"));

        findMatchingEntry.setIsAccepted("Y");
        MatchingEntity findMatching = matchingRepository.findById(findMatchingEntry.getMatchingId())
                .orElseThrow(() -> new EntityNotFoundException("MatchingEntity not found"));
        findMatching.setCurrentParticipant(findMatching.getCurrentParticipant() + 1);   // 현재 인원 1명 추가
        if(findMatching.getCurrentParticipant() >= findMatching.getMaximumParticipant()) {
            findMatching.setIsCompleted("Y");
//            CompletedMatching(findMatching); // 프로젝트 룸 생성
        }

        matchingRepository.save(findMatching);
        matchingEntryRepository.save(findMatchingEntry);
        resultMatchingEntryEntity2MatchingDTO(findMatchingEntry, matchingEntryDTO);
    }

    private void CompletedMatching(MatchingEntity findMatching) {
        int managerId = findMatching.getMemberId(); // 방장 아이디
        RequestParticipantDTO participantDTO = new RequestParticipantDTO(managerId, true);
        List<MatchingEntryDTO> findAcceptedMember = queryService.getMatchingEntryByMatchingId(findMatching.getId(), true);

        List<RequestParticipantDTO> participantList = new ArrayList<>(); // 팀원 리스트
        participantList.add(participantDTO);
        for (MatchingEntryDTO member : findAcceptedMember) {
            participantDTO = new RequestParticipantDTO(member.getId(), false);
            participantList.add(participantDTO);
        }

        RequestProjectRoomDTO projectRoomDTO = new RequestProjectRoomDTO();
        projectRoomDTO.setName("매칭 프로젝트 방");
        projectRoomDTO.setContent("자동 매칭으로 생성된 프로젝트 방 입니다.");
        projectRoomDTO.setMaximumParticipant(findMatching.getMaximumParticipant());
        projectRoomDTO.setTechnologyCategory(findMatching.getTechnologyCategoryId());
        projectRoomDTO.setDurationTime("3");
        projectRoomDTO.setParticipantList(participantList);
        projectRoomService.createMatchingProject(projectRoomDTO);
    }

    @Override
    @Transactional
    public void registTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO) {
        TechnologyCategoryEntity registTechnologyCategory = technologyCategoryDTO2TechnologyCategoryEntity(technologyCategoryDTO);
        registTechnologyCategory.setIsDeleted("N");
        technologyCategoryRepository.save(registTechnologyCategory);
        resultTechnologyCategoryEntity2TechnologyCategoryDTO(registTechnologyCategory, technologyCategoryDTO);
    }

    @Override
    @Transactional
    public void modifyTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO) {

        TechnologyCategoryEntity findTechnologyCategory = technologyCategoryRepository.findById(technologyCategoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("TechnologyCategoryEntity not found"));

        if(technologyCategoryDTO.getName() != null) {
            findTechnologyCategory.setName(technologyCategoryDTO.getName());
        }
        if(technologyCategoryDTO.getRefTechnologyCategoryId() != null) {
            findTechnologyCategory.setRefTechnologyCategoryId(technologyCategoryDTO.getRefTechnologyCategoryId());
        } else if(!Objects.equals(technologyCategoryDTO.getRefTechnologyCategoryId(), findTechnologyCategory.getRefTechnologyCategoryId())) {
            findTechnologyCategory.setRefTechnologyCategoryId(technologyCategoryDTO.getRefTechnologyCategoryId());
        }
        technologyCategoryRepository.save(findTechnologyCategory);
        resultTechnologyCategoryEntity2TechnologyCategoryDTO(findTechnologyCategory, technologyCategoryDTO);
    }

    @Override
    @Transactional
    public void deleteTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO) {
        TechnologyCategoryEntity findTechnologyCategory = technologyCategoryRepository.findById(technologyCategoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("TechnologyCategoryEntity not found"));

        findTechnologyCategory.setIsDeleted("Y");
        technologyCategoryRepository.save(findTechnologyCategory);
        resultTechnologyCategoryEntity2TechnologyCategoryDTO(findTechnologyCategory, technologyCategoryDTO);
    }

    private void resultTechnologyCategoryEntity2TechnologyCategoryDTO(TechnologyCategoryEntity technologyCategoryEntity, CommandTechnologyCategoryDTO technologyCategoryDTO) {
        technologyCategoryDTO.setId(technologyCategoryEntity.getId());
        technologyCategoryDTO.setName(technologyCategoryEntity.getName());
        technologyCategoryDTO.setIsDeleted(technologyCategoryEntity.getIsDeleted());
        technologyCategoryDTO.setRefTechnologyCategoryId(technologyCategoryEntity.getRefTechnologyCategoryId());
    }

    private TechnologyCategoryEntity technologyCategoryDTO2TechnologyCategoryEntity(CommandTechnologyCategoryDTO technologyCategoryDTO) {
        TechnologyCategoryEntity technologyCategoryEntity = new TechnologyCategoryEntity();
        technologyCategoryEntity.setName(technologyCategoryDTO.getName());
        technologyCategoryEntity.setRefTechnologyCategoryId(technologyCategoryDTO.getRefTechnologyCategoryId());
        return technologyCategoryEntity;
    }

    private void resultMatchingEntryEntity2MatchingDTO(MatchingEntryEntity matchingEntryEntity, CommandMatchingEntryDTO matchingEntryDTO) {
        matchingEntryDTO.setId(matchingEntryEntity.getId());
        matchingEntryDTO.setMemberId(matchingEntryEntity.getMemberId());
        matchingEntryDTO.setIsAccepted(matchingEntryEntity.getIsAccepted());
        matchingEntryDTO.setIsCanceled(matchingEntryEntity.getIsCanceled());
        matchingEntryDTO.setMatchingId(matchingEntryEntity.getMatchingId());
        matchingEntryDTO.setAppliedDateAt(matchingEntryEntity.getAppliedDateAt());
    }

    private MatchingEntryEntity matchingEntryDTO2MatchingEntryEntity(CommandMatchingEntryDTO matchingEntryDTO) {
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
