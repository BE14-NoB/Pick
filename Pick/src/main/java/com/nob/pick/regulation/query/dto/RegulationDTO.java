package com.nob.pick.regulation.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegulationDTO {
    private int id;
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private boolean isDeleted;
    private int memberId;
}
