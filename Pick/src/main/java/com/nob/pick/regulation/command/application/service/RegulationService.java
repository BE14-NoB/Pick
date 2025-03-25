package com.nob.pick.regulation.command.application.service;

import com.nob.pick.regulation.command.application.dto.RegulationDTO;

public interface RegulationService {
    void registRegulation(RegulationDTO newRegulation);

    void deleteRegulation(int id);
}
