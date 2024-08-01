package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class RegisterController {

    private final WebClient webClient;

    @Autowired
    public RegisterController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDoc", new UserDoc());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDoc") UserDoc userDoc, Model model) {
        webClient.post()
                .uri("/api/users")
                .bodyValue(userDoc)
                .retrieve()
                .bodyToMono(UserDoc.class)
                .block();
        model.addAttribute("userDoc", userDoc);
        return "redirect:/home";
    }
}
