package com.nob.pick.matching.query.aggregate;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Getter
@ToString
public class Matching {
    private int id;
    private Date createdDateAt;
    private boolean isCompleted;
    private boolean isDeleted;
    private int levelRange;
    private int maximumParticipant;
    private int memberId;

    private List<TechnologyCategory> technologyCategories;
}
