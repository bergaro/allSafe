package ru.netology.safeapp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController("/")
public class AppController {

    @GetMapping("read")
    @Secured("ROLE_READ")
    public String getUnsafePage() {
        return "It's index page. User: " + SecurityContextHolder.getContext().getAuthentication().getName() + "." ;
    }

    @GetMapping("write")
    @RolesAllowed("ROLE_WRITE")
    public String getSafePage() {
        return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName() + "!";
    }

    @GetMapping("write-delete")
    @PreAuthorize("hasAnyRole('ROLE_WRITE','ROLE_DELETE')")
    public String getAdminPage() {
        return SecurityContextHolder.getContext().getAuthentication().getName() + " is administrator!";
    }

    @GetMapping("validate")
    @PostAuthorize("#username == authentication.principal.username")
    public  String getUserData(String username) {
        return "Username: " + username + ", is valid!";
    }



}
