package com.nob.pick.matching;

import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.dto.SearchMatchingDTO;
import com.nob.pick.matching.query.service.MatchingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class MatchingTest {
    @Autowired
    private MatchingService matchingService;

    @Test
    @DisplayName("전체 매칭방 조회 테스트")
    public void findAllMatching() {
        List<MatchingDTO> findAllMatching = matchingService.getMatching();
        if(findAllMatching.size() > 0) {
            findAllMatching.forEach(System.out::println);
        } else {
            System.out.println("Not found Matching");
        }
    }

    private static Stream<Arguments> getSearchMatchingInfo() {
        SearchMatchingDTO searchMatchingInfo = new SearchMatchingDTO();
        searchMatchingInfo.setMemberId(4);
        searchMatchingInfo.setTechnologyCategoryCode(1);
        return Stream.of(Arguments.arguments(searchMatchingInfo));
    }

    @DisplayName("매칭 기능 테스트")
    @ParameterizedTest
    @MethodSource("getSearchMatchingInfo")
    public void searchMatching(SearchMatchingDTO searchMatchingInfo) {
        Assertions.assertDoesNotThrow(() -> matchingService.getSearchMatching(searchMatchingInfo));
    }

}
