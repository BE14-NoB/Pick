package com.nob.pick.gitactivity.command.domain.repository;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 추후에 interface로 수정할 예정
@Component
public class GitHubTokenRepository {
    private final Map<Integer, String> store = new ConcurrentHashMap<>();

    public void save(Integer userId, String token) {
        store.put(userId, token);
    }

    public String get(Integer userId) {
        return store.get(userId);
    }
}
