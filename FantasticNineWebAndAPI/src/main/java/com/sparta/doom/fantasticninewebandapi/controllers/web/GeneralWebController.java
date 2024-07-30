package com.sparta.doom.fantasticninewebandapi.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GeneralWebController {
    @GetMapping("/")
    public RedirectView landingPage() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
