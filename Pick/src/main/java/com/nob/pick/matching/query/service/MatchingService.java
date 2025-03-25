package com.nob.pick.matching.query.service;

import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.dto.MatchingEntryDTO;
import com.nob.pick.matching.query.dto.SearchMatchingDTO;
import com.nob.pick.matching.query.dto.TechnologyCategoryDTO;

import java.util.List;

public interface MatchingService {

    List<MatchingDTO> getMatching();

    List<MatchingDTO> getMatchingByMatchingId(int matchingId);

    List<MatchingDTO> getMatchingByTechnologyCategoryId(int technologyCategoryId);

    List<MatchingEntryDTO> getMatchingEntryByMatchingId(int matchingId, boolean status);

    List<TechnologyCategoryDTO> getTechnologyCategory();

    List<TechnologyCategoryDTO> getTechnologyCategoryByTechnologyCategoryId(int technologyCategoryId);

    List<TechnologyCategoryDTO> getSubTechnologyCategoryByRefTechnologyCategoryId(int refTechnologyCategoryId);

    List<TechnologyCategoryDTO> getParentTechnologyCategory();

    List<MatchingDTO> getSearchMatching(SearchMatchingDTO searchMatchingDTO);
    List<MatchingDTO> getSearchMatchingTest(SearchMatchingDTO searchMatchingDTO, int memberLevel);
}
