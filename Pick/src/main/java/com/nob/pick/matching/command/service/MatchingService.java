package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.dto.CommandMatchingDTO;
import com.nob.pick.matching.command.dto.RegistMatchingEntryDTO;

public interface MatchingService {
    void registMatching(CommandMatchingDTO matchingDTO);

    void modifyMatching(CommandMatchingDTO matchingDTO);

    void deleteMatching(CommandMatchingDTO matchingDTO);

    void registMatchingEntry(RegistMatchingEntryDTO matchingEntryDTO);

    void deleteMatchingEntry(RegistMatchingEntryDTO matchingEntryDTO);
}
