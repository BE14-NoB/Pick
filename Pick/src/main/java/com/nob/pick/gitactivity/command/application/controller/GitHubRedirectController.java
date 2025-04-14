package com.nob.pick.gitactivity.command.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GitHubRedirectController {

    @GetMapping("/api/github/success")
    public String redirectToFrontend() {
        return "redirect:http://localhost:5173/github/success";
    }

    @GetMapping("/api/github/error")
    public String redirectToFrontendError() {
        return "redirect:http://localhost:5173/github/error";
    }
}