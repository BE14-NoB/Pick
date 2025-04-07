package com.nob.pick.matching.query.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseMemberVO {
    private int id;
    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private String password;
    private String nickname;
    private int reportedCount;
}
