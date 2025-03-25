package com.nob.pick.matching;

import com.nob.pick.matching.query.dto.MatchingDTO;
import com.nob.pick.matching.query.service.MatchingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class MatchingTest {
    @Autowired
    private MatchingService matchingService;


    @Test
    @DisplayName("전체 매칭방 조회")
    public void findAllMatching() {
        List<MatchingDTO> findAllMatching = matchingService.getMatching();
        if(findAllMatching.size() > 0) {
            findAllMatching.forEach(System.out::println);
        } else {
            System.out.println("Not found Matching");
        }
    }
}
