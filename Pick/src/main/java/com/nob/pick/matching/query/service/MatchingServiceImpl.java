package com.nob.pick.matching.query.service;

import com.nob.pick.badge.query.service.MemberBadgeQueryService;
import com.nob.pick.matching.query.aggregate.Matching;
import com.nob.pick.matching.query.aggregate.MatchingEntry;
import com.nob.pick.matching.query.aggregate.TechnologyCategory;
import com.nob.pick.matching.query.dto.*;
import com.nob.pick.matching.query.mapper.MatchingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nob.pick.matching.query.infrastructure.MatchingMemberServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MatchingServiceImpl implements MatchingService{

    private final MatchingMapper matchingMapper;
    private final MatchingMemberServiceClient matchingMemberServiceClient;
    private final MemberBadgeQueryService memberBadgeQueryService;


    @Autowired
    public MatchingServiceImpl(MatchingMapper matchingMapper, MatchingMemberServiceClient matchingMemberServiceClient, MemberBadgeQueryService memberBadgeQueryService) {
        this.matchingMapper = matchingMapper;
        this.matchingMemberServiceClient = matchingMemberServiceClient;
        this.memberBadgeQueryService = memberBadgeQueryService;
    }

    @Override
    public List<MatchingDTO> getMatching() {

        List<Matching> matchingList = matchingMapper.selectAllMatching();
        log.info(""+matchingList);
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
        List<MatchingEntry> matchingEntryList;
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
        // 신청한 회원 정보
        Map<String, Object> member = matchingMemberServiceClient.getUserInfo();
        log.info("member: {}", member);
        // 신청자 레벨
        int memberLevel = matchingMemberServiceClient.getMemberProfileByMemberId((int)member.get("id")).getLevel();

        // 전체 방 조회
        List<Matching> matchingList = matchingMapper.selectAllMatching();

        List<MatchingInfo> matchingInfoList = matchingList.stream()
                .map(matching -> {
                    int level = matchingMemberServiceClient.getMemberProfileByMemberId(matching.getMemberId()).getLevel();
                    return new MatchingInfo(matching.getId(), matching.getMemberId(), level);
                })
                .collect(Collectors.toList());

        // 레벨에 획득 뱃지 advantage 추가
        memberLevel += memberBadgeQueryService.getTotalAdvantageByMemberId((int)member.get("id"));
        log.info("advantage: {}", memberBadgeQueryService.getTotalAdvantageByMemberId((int)member.get("id")));
        
        log.info("managerList: {}", matchingInfoList);

        // 매칭 정보 입력
        MatchingInfoDTO matchingInfoDTO = new MatchingInfoDTO();
        matchingInfoDTO.setMemberLevel(memberLevel);
        matchingInfoDTO.setMatchingInfoList(matchingInfoList);
        // null: 선택 안함
        matchingInfoDTO.setTechnologyCategoryId(searchMatchingDTO.getTechnologyCategoryId());
        matchingInfoDTO.setDurationTimeRangeMin(searchMatchingDTO.getDurationTimeRangeMin());
        matchingInfoDTO.setDurationTimeRangeMax(searchMatchingDTO.getDurationTimeRangeMax());
        matchingInfoDTO.setMaximumParticipantRangeMin(searchMatchingDTO.getMaximumParticipantRangeMin());
        matchingInfoDTO.setMaximumParticipantRangeMax(searchMatchingDTO.getMaximumParticipantRangeMax());

        log.info("matchingInfo: {}", matchingInfoDTO);

        List<Matching> Result = matchingMapper.searchMatching(matchingInfoDTO);

        return matching2MatchingDTO(Result);
    }

    @Override
    public List<MatchingDTO> getMatchingByManagerId(int managerId) {

        List<Matching> matchingList = matchingMapper.selectMatchingByManagerId(managerId);

        return matching2MatchingDTO(matchingList);
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
            matchingDTO.setDurationTime(matching.getDurationTime());

            List<TechnologyCategory> technologyCategoryList = matching.getTechnologyCategories();
            List<TechnologyCategoryDTO> technologyCategoryDTOList = technologyCategory2TechnologyCategoryDTO(technologyCategoryList);

            matchingDTO.setTechnologyCategories(technologyCategoryDTOList);
            matchingDTOList.add(matchingDTO);
        }

        return matchingDTOList;
    }
}
