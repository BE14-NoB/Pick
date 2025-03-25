package com.nob.pick.matching.command.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommandTechnologyCategoryDTO {
    private int id;
    private String name;
    private String isDeleted;
    private Integer refTechnologyCategoryId;
}
