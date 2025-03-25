package com.nob.pick.matching.query.aggregate;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Getter
@ToString
public class Matching {
    private int id;
    private LocalDateTime createdDateAt;
    private boolean isCompleted;
    private boolean isDeleted;
    private int levelRange;
    private int maximumParticipant;
    private int currentParticipant;
    private int memberId;

    private List<TechnologyCategory> technologyCategories;
}
