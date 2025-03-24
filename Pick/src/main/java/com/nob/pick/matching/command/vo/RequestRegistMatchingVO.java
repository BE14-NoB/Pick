package com.nob.pick.matching.command.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistMatchingVO {
    private int levelRange;
    private int memberId;
    private int technologyCategoryId;
}
