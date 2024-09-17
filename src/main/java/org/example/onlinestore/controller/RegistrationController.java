package org.example.onlinestore.controller;

import org.example.onlinestore.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
}
