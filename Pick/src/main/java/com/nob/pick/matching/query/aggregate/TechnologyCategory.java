package com.nob.pick.matching.query.aggregate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TechnologyCategory {
    private int id;
    private String name;
    private boolean isDeleted;
    private Integer refTechnologyCategoryId;
}
