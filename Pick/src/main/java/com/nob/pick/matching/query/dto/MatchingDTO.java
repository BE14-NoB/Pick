package com.nob.pick.matching.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MatchingDTO {
    private int id;
    private int memberId;
    private int levelRange;

    private List<TechnologyCategoryDTO> technologyCategories;
}
