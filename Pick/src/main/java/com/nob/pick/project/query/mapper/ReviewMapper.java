package com.nob.pick.project.query.mapper;

import com.nob.pick.project.query.aggregate.MemberReview;
import com.nob.pick.project.query.aggregate.ProjectReview;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ProjectReview> selectProjectReviewList(int projectId);

    List<MemberReview> selectMemberReviewList(int projectId);
}
