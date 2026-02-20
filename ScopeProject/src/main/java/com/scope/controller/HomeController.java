package com.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("activePage", "courses");
        return "courses";
    }

    @GetMapping("/placements")
    public String placements(Model model) {
        model.addAttribute("activePage", "placements");
        return "placements";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/faq")
    public String faq(Model model) {
        model.addAttribute("activePage", "faq");
        return "faq";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("activePage", "reviews");
        return "reviews";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("activePage", "contact");
        return "contact";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("activePage", "registration");
        return "registration";
    }
}