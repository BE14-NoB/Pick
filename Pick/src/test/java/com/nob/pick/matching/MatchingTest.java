package com.nob.pick.matching;


import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.dto.MatchingEntryDTO;
import com.nob.pick.matching.query.dto.SearchMatchingDTO;
import com.nob.pick.matching.query.dto.TechnologyCategoryDTO;
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

    @Test
    @DisplayName("전체 매칭방 조회 테스트")
    public void findAllMatchingTest() {
        List<MatchingDTO> result = matchingService.getMatching();
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("매칭방 id로 매칭방 조회")
    public void findMatchingByMatchingIdTest() {
        int matchingId = 4;
        List<MatchingDTO> result = matchingService.getMatchingByMatchingId(matchingId);
        result.forEach(System.out::println);
    }
    @Test
    @DisplayName("기술 카테고리 id로 매칭방 조회 테스트")
    public void findMatchingByTechnologyCategoryIdTest() {
        int technologyCategoryId = 4;
        List<MatchingDTO> result = matchingService.getMatchingByTechnologyCategoryId(technologyCategoryId);
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("매칭방 id로 신청자 조회 테스트")
    public void findMatchingEntryByMatchingIdTest() {
        int matchingId = 7;
        List<MatchingEntryDTO> result = matchingService.getMatchingEntryByMatchingId(matchingId, false);
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("기술 카테고리 조회 테스트")
    public void findTechnologyCategoryTest() {
        List<TechnologyCategoryDTO> result = matchingService.getTechnologyCategory();
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("기술 카테고리 id로 기술 카테고리 조회 테스트")
    public void findTechnologyCategoryByTechnologyCategoryIdTest() {
        int technologyCategoryId = 7;
        List<TechnologyCategoryDTO> result = matchingService.getTechnologyCategoryByTechnologyCategoryId(technologyCategoryId);
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("상위 기술 카테고리로 하위 기술 카테고리 조회 테스트")
    public void findSubTechnologyCategoryByRefTechnologyCategoryIdTest() {
        int refTechnologyCategoryId = 3;
        List<TechnologyCategoryDTO> result = matchingService.getSubTechnologyCategoryByRefTechnologyCategoryId(refTechnologyCategoryId);
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("방장 id로 매칭방 조회")
    public void findMatchingByManagerIdTest() {
        int managerId = 2;
        List<MatchingDTO> result = matchingService.getMatchingByManagerId(managerId);
        result.forEach(System.out::println);
    }

}
