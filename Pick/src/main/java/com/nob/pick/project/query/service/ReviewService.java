package com.nob.pick.project.query.service;

import com.nob.pick.project.query.aggregate.MemberReview;
import com.nob.pick.project.query.aggregate.ProjectReview;

import java.util.List;

public interface ReviewService {
    List<ProjectReview> getProjectReviews(int projectId);

    List<MemberReview> getMemberReviews(int projectId);
}
