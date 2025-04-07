package com.nob.pick.matching;


import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.dto.SearchMatchingDTO;
import com.nob.pick.matching.query.service.MatchingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class MatchingTest {
    @Autowired
    private MatchingService matchingService;
    private com.nob.pick.matching.command.service.MatchingService commandService;

    @Test
    @DisplayName("전체 매칭방 조회 테스트")
    public void findAllMatchingTest() {
        Assertions.assertDoesNotThrow(() -> matchingService.getMatching());
    }

    @Test
    @DisplayName("기술 카테고리 id로 매칭방 조회 테스트")
    public void findMatchingByTechnologyCategoryIdTest() {
        int technologyCategoryId = 4;
        Assertions.assertDoesNotThrow(() -> matchingService.getMatchingByTechnologyCategoryId(technologyCategoryId));
    }

    @Test
    @DisplayName("매칭방 id로 신청자 조회")
    public void findMatchingEntryByMatchingIdTest() {
        int matchingId = 7;
        Assertions.assertDoesNotThrow(() -> matchingService.getMatchingEntryByMatchingId(matchingId, false));
    }
}
