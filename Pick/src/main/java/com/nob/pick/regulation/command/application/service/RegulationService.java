package com.nob.pick.regulation.command.application.service;

import com.nob.pick.regulation.command.application.dto.RegulationDTO;

import java.time.LocalDate;

public interface RegulationService {
    LocalDate registRegulation(RegulationDTO newRegulation);

    void deleteRegulation(int id);
}
