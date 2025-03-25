package com.nob.pick.project.query.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseProjectVO {
    private int id;
    private String name;
    private int maximumParticipant;
    private String startDate;
    private String endDate;
    private String durationTime;
    private String thumbnailImage;
    private String introduction;

    private int technologyCategoryId;
    private String technologyCategoryName;
}
