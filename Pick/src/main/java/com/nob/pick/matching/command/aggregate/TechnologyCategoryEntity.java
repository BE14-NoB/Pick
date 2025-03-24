package com.nob.pick.matching.command.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name="technology_category")
public class TechnologyCategoryEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    @Column(name="is_deleted", nullable=false, columnDefinition = "VARCHAR(4) DEFAULT 'N'")
    private String isDeleted;

    @Column(name="ref_technology_category_id")
    private Integer refTechnologyCategoryId;
}
