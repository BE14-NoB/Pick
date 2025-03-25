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

    @Test
    @DisplayName("매칭 조건 정보와 신청자 레벨로 자동 매칭 테스트")
    public void searchMatchingTest() {
        int memberLevel = 10;
        SearchMatchingDTO searchMatchingDTO = new SearchMatchingDTO();
        searchMatchingDTO.setMemberId(5);
        searchMatchingDTO.setTechnologyCategoryCode(2); // 모바일
        List<MatchingDTO> result = matchingService.getSearchMatchingTest(searchMatchingDTO, memberLevel);
        if (!result.isEmpty()) {
            result.forEach(System.out::println);
        } else {
            System.out.println("매칭 조건에 맞는 매칭방이 없습니다.");
        }
    }
//    private static Stream<Arguments> getSearchMatchingInfo() {
//        SearchMatchingDTO searchMatchingInfo = new SearchMatchingDTO();
//        searchMatchingInfo.setMemberId(4);
//        searchMatchingInfo.setTechnologyCategoryCode(1);
//        return Stream.of(Arguments.arguments(searchMatchingInfo));
//    }

//    @DisplayName("매칭 기능 테스트")
//    @ParameterizedTest
//    @MethodSource("getSearchMatchingInfo")
//    public void searchMatching(SearchMatchingDTO searchMatchingInfo) {
//        Assertions.assertDoesNotThrow(() -> matchingService.getSearchMatching(searchMatchingInfo));
//    }

}
