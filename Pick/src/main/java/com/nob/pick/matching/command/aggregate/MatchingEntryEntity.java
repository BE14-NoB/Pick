package com.nob.pick.matching.command.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name="matching_entry")
public class MatchingEntryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="applied_date_at", nullable=false)
    private String appliedDateAt;

    @Column(name="is_canceled", nullable=false)
    private String isCanceled;

    @Column(name="is_accepted", nullable = false)
    private String isAccepted;

    @Column(name="member_id", nullable=false)
    private int memberId;

    @Column(name="matching_id", nullable=false)
    private int matchingId;
}
