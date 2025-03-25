package com.nob.pick.regulation.query.service;

import com.nob.pick.regulation.query.dao.RegulationMapper;
import com.nob.pick.regulation.query.dto.RegulationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("QueryRegulationService")
public class RegulationServiceImpl implements RegulationService {

    private final RegulationMapper regulationMapper;

    @Autowired
    public RegulationServiceImpl(RegulationMapper regulationMapper) {
        this.regulationMapper = regulationMapper;
    }

    @Override
    public List<RegulationDTO> findAllRegulations() {
        return regulationMapper.selectAllRegulations();
    }

    @Override
    public List<RegulationDTO> findRegulationByMemberId(int memberId) {
        return regulationMapper.selectRegulationsByMemberId(memberId);
    }

    @Override
    public List<RegulationDTO> findRegulationsByDeletion(boolean isDeleted) {
        return regulationMapper.selectRegulationsByDeletion(isDeleted);
    }
}
