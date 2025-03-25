package com.nob.pick.report.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.model.TypeDefinition;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name="report_reason")
@Builder
public class ReportReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "reason", nullable = false,unique = true, columnDefinition = "TEXT")
    private String reason;
}
