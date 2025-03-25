package com.nob.pick.regulation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Table(name="regulation")
public class Regulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Builder.Default
    @Column(name="is_deleted", nullable = false, length = 4)
    private String isDeleted = "N";

    @Column(name = "member_id", nullable = false)
    private int memberId;

    // soft delete 처리 메서드
    public void markAsDeleted() {
        this.isDeleted = "Y";
    }
}
