package com.nob.pick.matching.command.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name="matching")
public class MatchingEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="created_date_at", nullable = false)
    private String createdDateAt;

    @Column(name="is_completed", nullable = false, columnDefinition = "VARCHAR(4) DEFAULT 'N'")
    private String isCompleted;

    @Column(name="level_range", nullable = false)
    private int levelRange;

    @Column(name="member_id", nullable = false)
    private int memberId;

    @Column(name="technology_category_id", nullable = false)
    private int technologyCategoryId;
}
