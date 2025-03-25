package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseTechnologyCategoryVO {
    private int id;
    private String name;
    private String isDeleted;
    private Integer refTechnologyCategoryId;
}
