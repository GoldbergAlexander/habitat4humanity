package com.agoldberg.hercules.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {
    @GetMapping("/login")
    public String showLogin(){
        return "login/Login";
    }

    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    @GetMapping("/entry")
    public String showEntry() {
        return "entry";
    }

    @GetMapping("/reporting")
    public String showReporting() {
        return "reporting";
    }

    @GetMapping("/admin")
    public String showAdmin() {
        return "admin";
    }

}
