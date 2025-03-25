package com.nob.pick.matching.query.service;

import com.nob.pick.matching.query.aggregate.Matching;
import com.nob.pick.matching.query.aggregate.MatchingEntry;
import com.nob.pick.matching.query.aggregate.TechnologyCategory;
import com.nob.pick.matching.query.dto.*;
import com.nob.pick.matching.query.mapper.MatchingMapper;
import com.nob.pick.member.query.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MatchingServiceImpl implements MatchingService{

    private final MatchingMapper matchingMapper;
    private final MemberService memberService;
    @Autowired
    public MatchingServiceImpl(MatchingMapper matchingMapper, MemberService memberService) {
        this.matchingMapper = matchingMapper;
        this.memberService = memberService;
    }

    @Override
    public List<MatchingDTO> getMatching() {

        List<Matching> matchingList = matchingMapper.selectAllMatching();

        return matching2MatchingDTO(matchingList);
    }

    @Override
    public List<MatchingDTO> getMatchingByMatchingId(int matchingId) {

        List<Matching> matchingList = matchingMapper.selectMatchingByMatchingId(matchingId);

        return matching2MatchingDTO(matchingList);
    }

    @Override
    public List<MatchingDTO> getMatchingByTechnologyCategoryId(int technologyCategoryId) {

        List<Matching> matchingList = matchingMapper.selectMatchingByTechnologyCategoryId(technologyCategoryId);

        return matching2MatchingDTO(matchingList);
    }

    @Override
    public List<MatchingEntryDTO> getMatchingEntryByMatchingId(int matchingId, boolean status) {
        List<MatchingEntry> matchingEntryList = new ArrayList<>();
        if(status)
            matchingEntryList = matchingMapper.selectMatchingEntryByAccepted(matchingId);
        else
            matchingEntryList = matchingMapper.selectMatchingEntryByMatchingId(matchingId);

        return matchingEntry2MatchingEntryDTO(matchingEntryList);
    }

    @Override
    public List<TechnologyCategoryDTO> getTechnologyCategory() {

        List<TechnologyCategory> technologyCategoryList = matchingMapper.selectAllTechnologyCategory();

        return technologyCategory2TechnologyCategoryDTO(technologyCategoryList);
    }

    @Override
    public List<TechnologyCategoryDTO> getTechnologyCategoryByTechnologyCategoryId(int technologyCategoryId) {

        List<TechnologyCategory> technologyCategoryList = matchingMapper.selectTechnologyCategoryByTechnologyCategoryId(technologyCategoryId);

        return technologyCategory2TechnologyCategoryDTO(technologyCategoryList);
    }

    @Override
    public List<TechnologyCategoryDTO> getSubTechnologyCategoryByRefTechnologyCategoryId(int refTechnologyCategoryId) {

        List<TechnologyCategory> subTechnologyCategoryList = matchingMapper.selectSubTechnologyCategoryByRefTechnologyCategoryId(refTechnologyCategoryId);

        return technologyCategory2TechnologyCategoryDTO(subTechnologyCategoryList);
    }

    @Override
    public List<TechnologyCategoryDTO> getParentTechnologyCategory() {

        List<TechnologyCategory> parentTechnologyCategoryList = matchingMapper.selectParentTechnologyCategory();

        return technologyCategory2TechnologyCategoryDTO(parentTechnologyCategoryList);
    }

    @Override
    @Transactional
    public List<MatchingDTO> getSearchMatching(SearchMatchingDTO searchMatchingDTO) {
        // 신청자 레벨

        int memberLevel = memberService.findMemberInfoById(searchMatchingDTO.getMemberId()).getId();

        log.info("searchMatchingDTO: {}", searchMatchingDTO);
        // 전체 방 조회
        List<Matching> matchingList = matchingMapper.selectAllMatching();

        List<MatchingInfo> matchingInfoList = matchingList.stream()
                .map(matching -> {
                    int level = memberService.findMemberInfoById(searchMatchingDTO.getMemberId()).getId();
                    return new MatchingInfo(matching.getId(), matching.getMemberId(), level);
                })
                .collect(Collectors.toList());

        log.info("managerList: {}", matchingInfoList);
        MatchingInfoDTO matchingInfoDTO = new MatchingInfoDTO();
        matchingInfoDTO.setMemberLevel(memberLevel);
        matchingInfoDTO.setMatchingInfoList(matchingInfoList);
        if(searchMatchingDTO.getTechnologyCategoryCode() != null ) {
            matchingInfoDTO.setTechnologyCategoryId(searchMatchingDTO.getTechnologyCategoryCode());
        }

        log.info("matchingInfo: {}", matchingInfoDTO);

        List<Matching> Result = matchingMapper.searchMatching(matchingInfoDTO);

        return matching2MatchingDTO(Result);
    }

    private List<TechnologyCategoryDTO> technologyCategory2TechnologyCategoryDTO(List<TechnologyCategory> technologyCategoryList) {

        List<TechnologyCategoryDTO> technologyCategoryDTOList = new ArrayList<>();

        for (TechnologyCategory technologyCategory : technologyCategoryList) {
            TechnologyCategoryDTO technologyCategoryDTO = new TechnologyCategoryDTO();
            technologyCategoryDTO.setId(technologyCategory.getId());
            technologyCategoryDTO.setName(technologyCategory.getName());
            technologyCategoryDTO.setRefTechnologyCategoryId(technologyCategory.getRefTechnologyCategoryId());

            technologyCategoryDTOList.add(technologyCategoryDTO);
        }

        return technologyCategoryDTOList;
    }

    private List<MatchingEntryDTO> matchingEntry2MatchingEntryDTO(List<MatchingEntry> matchingEntryList) {

        List<MatchingEntryDTO> matchingEntryDTOList = new ArrayList<>();

        for(MatchingEntry matchingEntry : matchingEntryList) {
            MatchingEntryDTO matchingEntryDTO = new MatchingEntryDTO();
            matchingEntryDTO.setId(matchingEntry.getId());
            matchingEntryDTO.setMemberId(matchingEntry.getMemberId());
            matchingEntryDTO.setMatchingId(matchingEntry.getMatchingId());

            matchingEntryDTOList.add(matchingEntryDTO);
        }

        return matchingEntryDTOList;
    }

    private List<MatchingDTO> matching2MatchingDTO(List<Matching> matchingList) {

        List<MatchingDTO> matchingDTOList = new ArrayList<>();

        for (Matching matching : matchingList) {
            MatchingDTO matchingDTO = new MatchingDTO();
            matchingDTO.setId(matching.getId());
            matchingDTO.setMemberId(matching.getMemberId());
            matchingDTO.setLevelRange(matching.getLevelRange());
            matchingDTO.setMaximumParticipant(matching.getMaximumParticipant());
            matchingDTO.setCurrentParticipant(matching.getCurrentParticipant());

            List<TechnologyCategory> technologyCategoryList = matching.getTechnologyCategories();
            List<TechnologyCategoryDTO> technologyCategoryDTOList = technologyCategory2TechnologyCategoryDTO(technologyCategoryList);

            matchingDTO.setTechnologyCategories(technologyCategoryDTOList);
            matchingDTOList.add(matchingDTO);
        }

        return matchingDTOList;
    }
}
