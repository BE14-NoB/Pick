package com.nob.pick.gitactivity.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Table(name = "GITHUB_ACCOUNT")
public class GitHubAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "access_token")
    private String accessToken;

    public void updateToken(String token) {
        this.accessToken = token;
    }
}