package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyTechnologyCategoryVO {
    private int id;
    private String name;
    private Integer refTechnologyCategoryId;
}
