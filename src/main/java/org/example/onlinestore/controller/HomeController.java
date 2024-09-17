package org.example.onlinestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Principal principal ){
        if(principal != null){
            return "home";
        }
        else {
        return "redirect:/login";
        }
    }

    @GetMapping("/management")
    public String management(){
        return "management";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

}
