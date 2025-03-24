package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.dto.CommandMatchingDTO;

public interface MatchingService {
    void registMatching(CommandMatchingDTO matchingDTO);

    void modifyMatching(CommandMatchingDTO matchingDTO);

    void deleteMatching(CommandMatchingDTO matchingDTO);
}
