package org.example.onlinestore.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.service.UserService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/adduser")
    public ModelAndView addUser(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("role") String role){

        User user = new User(userName,password,role);
        userService.createUser(user);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","New user");
        mv.addObject("addedOrRemoved","added");
        mv.setViewName("success.jsp");

        return mv;
    }

    @PostMapping("/removeuser")
    public ModelAndView removeUser(@RequestParam("userID") int userID){
        User user = new User(userID);
        userService.deleteUser(user);

        ModelAndView mv = new ModelAndView();
        mv.addObject("newOrNot","User");
        mv.addObject("addedOrRemoved","removed");
        mv.setViewName("success.jsp");

        return mv;
    }

    @GetMapping("/showusers")
    public ModelAndView showUsers(){
        List<User> allUsersList = userService.showUsers();
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",allUsersList);
        mv.setViewName("allusers.jsp");
        return mv;
    }

    @GetMapping("/getuser")
    public ModelAndView getUser(@RequestParam("userID") int userID){
        User user = new User(userID);
        Optional<User> wantedUser = userService.showUserByID(user);
        List<User> users = new ArrayList<>();
        wantedUser.ifPresent(users::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("users",users);
        mv.setViewName("allusers.jsp");
        return mv;
    }
}
