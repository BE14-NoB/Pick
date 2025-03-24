package com.nob.pick.matching.command.service;

import com.nob.pick.matching.command.dto.MatchingDTO;

public interface JPAMatchingService {
    void registMatching(MatchingDTO matchingDTO);

    void modifyMatching(MatchingDTO matchingDTO, int matchingId);
}
