package ru.netology.safeapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class AppController {

    @GetMapping("index")
    public String getUnsafePage() {
        return "It's index page.";
    }

    @GetMapping("secure")
    public String getSafePage() {
        return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName() + "!";
    }

    @GetMapping("admin")
    public String getAdminPage() {
        return SecurityContextHolder.getContext().getAuthentication().getName() + " is administrator!";
    }
}
