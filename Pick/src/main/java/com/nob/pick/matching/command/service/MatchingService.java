package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.dto.CommandMatchingEntryDTO;
import com.nob.pick.matching.command.dto.CommandTechnologyCategoryDTO;

public interface MatchingService {
    void registMatching(CommandMatchingDTO matchingDTO);

    void modifyMatching(CommandMatchingDTO matchingDTO);

    void deleteMatching(CommandMatchingDTO matchingDTO);

    void registMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO);

    void deleteMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO);

    void acceptMatchingEntry(CommandMatchingEntryDTO matchingEntryDTO);

    void registTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO);

    void modifyTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO);

    void deleteTechnologyCategory(CommandTechnologyCategoryDTO technologyCategoryDTO);
}
