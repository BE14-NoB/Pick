package com.nob.pick.gitactivity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitActivityController {

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @GetMapping("/gitInfo")
    public String gitInfo(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("user", user);
        return "gitInfo";
    }

    @GetMapping("/save-token")
    public String saveToken(@AuthenticationPrincipal OAuth2User user,
                            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client) {
        String accessToken = client.getAccessToken().getTokenValue();
        String githubUsername = user.getAttribute("login");

        // 👉 여기서 DB에 githubUsername + accessToken 저장하면 끝!
        return "redirect:/gitInfo";
    }

}
