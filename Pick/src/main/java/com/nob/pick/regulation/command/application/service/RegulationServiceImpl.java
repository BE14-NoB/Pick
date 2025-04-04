package com.nob.pick.regulation.command.application.service;

import com.nob.pick.regulation.command.application.dto.RegulationDTO;
import com.nob.pick.regulation.command.domain.aggregate.Regulation;
import com.nob.pick.regulation.command.domain.repository.RegulationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service("CommandRegulationService")
public class RegulationServiceImpl implements RegulationService {
    private final RegulationRepository regulationRepository;

    public RegulationServiceImpl(RegulationRepository regulationRepository) {
        this.regulationRepository = regulationRepository;
    }

    @Override
    @Transactional
    public LocalDate registRegulation(RegulationDTO regulationDTO) {
//        Member member = memberRepository.findById(reportDTO.getMemberId())
//                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 제재가 등록된 시점
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(regulationDTO.getCount(), startDate);

        Regulation regulation = Regulation.builder()
                .startDate(startDate.toString())
                .endDate(endDate.toString())
                .memberId(regulationDTO.getMemberId())
                .build();

        regulationRepository.save(regulation);
        return endDate;
    }

    // endDate 구하는 함수
    private LocalDate calculateEndDate(int count, LocalDate startDate) {
        return switch (count) {
            case 0 -> startDate.plusDays(14);
            case 1 -> startDate.plusDays(30);
            case 2 -> LocalDate.of(9999, 12, 31); // 영구정지
            default -> throw new IllegalArgumentException("유효하지 않은 제재 단계입니다.");
        };
    }


    @Override
    @Transactional
    public void deleteRegulation(int id) {
        Regulation foundRegulation = regulationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 규제 내역이 없습니다."));
        foundRegulation.markAsDeleted();
    }
}
