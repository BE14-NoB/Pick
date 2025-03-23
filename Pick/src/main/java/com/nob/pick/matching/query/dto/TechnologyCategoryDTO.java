package com.nob.pick.matching.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class  TechnologyCategoryDTO {
    private int id;
    private String name;
    private Integer refTechnologyCategoryId;
}
