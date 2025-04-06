package com.nob.pick.project.query.dto.enums;

import lombok.Getter;

@Getter
public enum TemplateType {
	SPRINT(0, "스프린트"),               // 스프린트
	BRAIN_STRORMING(1, "브레인스토밍"),   // 브레인스토밍
	SHORTCUT(2, "짧은회의"),            	// 짧은 회의
	ONE_TO_ONE(3, "일대일회의"),         	// 1:1 회의
	ALL_HANDS(4, "전체회의"),            	// 전사 회의(전체 회의)
	RETROSPECTIVE(5, "회고회의");		 	// 회고 회의

	private final int NUM;
	private final String DESCRIPTION;

	TemplateType(int num, String description) {
		NUM = num;
		DESCRIPTION = description;
	}

	public static TemplateType forNum(int num) {
		for(TemplateType type : TemplateType.values()) {
			if(type.NUM == num) {return type;}
		}
		throw new IllegalArgumentException("Invalid Num: " + num);
	}


}
