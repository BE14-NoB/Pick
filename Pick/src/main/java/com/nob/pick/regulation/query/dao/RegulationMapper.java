package com.nob.pick.regulation.query.dao;

import com.nob.pick.regulation.query.dto.RegulationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegulationMapper {
    List<RegulationDTO> selectAllRegulations();

    List<RegulationDTO> selectRegulationsByMemberId(int memberId);

    List<RegulationDTO> selectRegulationsByDeletion(boolean isDeleted);
}
