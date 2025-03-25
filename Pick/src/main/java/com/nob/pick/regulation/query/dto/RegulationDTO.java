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
    private java.util.Date startDate;
    private java.util.Date endDate;
    private boolean isDeleted;
    private int memberId;
}
