package com.chosu.jobssimpleboard.board.controller;


import com.chosu.jobssimpleboard.board.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginViewController {
    private LoginService loginService;

    @RequestMapping(value = "/")
    public String dashboard(){
        return "index";
    }

    @RequestMapping(value = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

    @RequestMapping(value = "/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/user/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("principal", principal.getName());
        return "dashboard";
    }

    @RequestMapping(value = "/admin/dashboard")
    public String adminDashboard(Model model, Principal principal){
        model.addAttribute("principal", principal.getName());
        return "admin-dashboard";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}
