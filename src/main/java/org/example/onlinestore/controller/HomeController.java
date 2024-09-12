package org.example.onlinestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/management")
    public String management(){
        return "management";
    }

    @GetMapping("/manageUsers")
    public String manageUsers(){
        return "manageUsers";
    }

    @GetMapping("/manageCarts")
    public String manageCarts(){
        return "manageCarts";
    }

    @GetMapping("/manageItems")
    public String manageItems(){
        return "manageItems";
    }

    @GetMapping("/manageStores")
    public String manageStores(){
        return "manageStores";
    }

    @GetMapping("/userCreation")
    public String userCreation(){
        return "userCreation";
    }

    @GetMapping("/userDeletion")
    public String userDeletion(){
        return "userDeletion";
    }

    @GetMapping("/userGet")
    public String userGet(){
        return "userGet";
    }

    @GetMapping("/allusers")
    public String allusers(){
        return "allusers";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/itemAddition")
    public String itemAddition(){
        return "itemAddition";
    }

    @GetMapping("/itemDeletion")
    public String itemDeletion(){
        return "itemDeletion";
    }

     @GetMapping("/itemGet")
    public String itemGet(){
        return "itemGet";
    }

    @GetMapping("/itemUpdate")
    public String itemUpdate(){
        return "itemUpdate";
    }

    @GetMapping("/allitems")
    public String allitems(){
        return "allitems";
    }

    @GetMapping("/storeDeletion")
    public String storeDeletion(){
        return "storeDeletion";
    }

    @GetMapping("/storeGet")
    public String storeGet(){
        return "storeGet";
    }

    @GetMapping("/allstores")
    public String allstores(){
        return "allstores";
    }

    @GetMapping("/cartDeletion")
    public String cartDeletion(){
        return "cartDeletion";
    }

    @GetMapping("/cartGet")
    public String cartGet(){
        return "cartGet";
    }

    @GetMapping("/allcarts")
    public String allcarts(){
        return "allcarts";
    }
}
