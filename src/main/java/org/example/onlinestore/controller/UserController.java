package org.example.onlinestore.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.entity.User;
import org.example.onlinestore.enums.UserRoles;
import org.example.onlinestore.enums.UserStatus;
import org.example.onlinestore.model.UserPrinciple;
import org.example.onlinestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/adduser")
    public String addUser(@RequestParam("userName") String userName,
                          @RequestParam ("email") String email,
                                @RequestParam("password") String password,
                                @RequestHeader(value = "Referer", required = false) String referer,
                                @RequestParam("role") String role, RedirectAttributes redirectAttributes){
        try {
            User user = new User(userName,password,role,email,UserStatus.ACTIVE.getStatus());
            userService.createUser(user);


            redirectAttributes.addFlashAttribute("message","New user has been added");

            return "redirect:" + (referer != null ? referer : "appManager");
        }

        catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:" + (referer != null ? referer : "appManager");
        }
    }

    @PostMapping("/registration")
    public String registration(@RequestParam ("email") String email,
                               @RequestParam("userName") String userName,
                               @RequestParam("password") String password,
                               @RequestHeader(value = "Referer", required = false) String referer, RedirectAttributes redirectAttributes){
        try {
            String role = UserRoles.USER.getRole();
            String status = UserStatus.ACTIVE.getStatus();

            User user = new User(userName,password,role,email,status);
            userService.createUser(user);

            redirectAttributes.addFlashAttribute("message","New account has been created");

            return "redirect:/login";
        }

        catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:" + (referer != null ? referer : "login");
        }
    }

    @PostMapping("/removeuser")
    public String removeUser(@RequestParam("userID") int userID,
                                   RedirectAttributes redirectAttributes,
                                   @RequestHeader(value = "Referer", required = false) String referer){
        User user = new User(userID);
        userService.deleteUser(user);

        redirectAttributes.addFlashAttribute("message","User has been removed");


        return "redirect:" + (referer != null ? referer : "appManager") ;
    }

    @GetMapping("/showusers")
    public ModelAndView showUsers(){
        List<User> allUsersList = userService.showUsers();
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",allUsersList);
        mv.setViewName("user/userManagement");
        return mv;
    }

    @PostMapping("/updateuser")
    public String updateUser(@RequestParam("userID") int userID,
                             @RequestParam("userName") String userName,
                                @RequestParam ("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("address") String address,
                                @RequestParam("payment") String payment,
                                @RequestHeader(value = "Referer", required = false) String referer,
                                @RequestParam(value = "role", required = false) String role,
                                 RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((UserPrinciple) authentication.getPrincipal()).getUserId();
        User currentUser = userService.showUserByID(new User(userId)).get();

        if (role == null){
            role = "USER";
        }
        try {
            User user = new User();
            user.setUserID(userID);
            user.setUsername(userName);
            user.setEmail(email);
            user.setPassword(password);
            user.setAddress(address);
            user.setPaymentMethod(payment);
            user.setRole(role);

            userService.remakeUser(user);

            if (currentUser.getRole().equals(UserRoles.ADMIN.getRole())){
                redirectAttributes.addFlashAttribute("message","User has been updated");
                return "redirect:" + (referer != null ? referer : "appManager");
            }
            else {
                return "redirect:/logout";
            }
        }

        catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:" + (referer != null ? referer : "appManager");
        }
    }

    @GetMapping("/fillUserInfo")
    public ModelAndView fillUserInfo(@RequestParam(value = "userID",required = false) Integer userID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int currentUserID = ((UserPrinciple) authentication.getPrincipal()).getUserId();

        if(userID == null){
            userID = currentUserID;
        }

        User currentUser = userService.showUserByID(new User(currentUserID)).get();
        User user = userService.showUserByID(new User(userID)).get();


        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("currentUser",currentUser);
        mv.setViewName("user/userUpdate");

        return mv;
    }

    @PostMapping("/userSuspend")
    public String userSuspend(@RequestParam("userID") int userID,
                                @RequestHeader(value = "Referer", required = false) String referer,
                                RedirectAttributes redirectAttributes){
        try {
            User user = userService.showUserByID(new User(userID)).get();
            user.setUserStatus(UserStatus.SUSPEND.getStatus());
            userService.changeUserStatus(user);


            redirectAttributes.addFlashAttribute("message","User has been suspended");

            return "redirect:" + (referer != null ? referer : "appManager");
        }

        catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:" + (referer != null ? referer : "appManager");
        }
    }

    @PostMapping("/userActivate")
    public String userActivate(@RequestParam("userID") int userID,
                                @RequestHeader(value = "Referer", required = false) String referer,
                                RedirectAttributes redirectAttributes){
        try {
            User user = userService.showUserByID(new User(userID)).get();
            user.setUserStatus(UserStatus.ACTIVE.getStatus());
            userService.changeUserStatus(user);


            redirectAttributes.addFlashAttribute("message","User has been activated");

            return "redirect:" + (referer != null ? referer : "appManager");
        }

        catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("error",e.getMessage());

            return "redirect:" + (referer != null ? referer : "appManager");
        }
    }

    @GetMapping("/getuser")
    public ModelAndView getUser(@RequestParam("userID") int userID){
        User user = new User(userID);
        Optional<User> wantedUser = userService.showUserByID(user);
        List<User> users = new ArrayList<>();
        wantedUser.ifPresent(users::add);

        ModelAndView mv = new ModelAndView();
        mv.addObject("users",users);
        mv.setViewName("user/userManagement");
        return mv;
    }

    @GetMapping("/searchusers")
    public ModelAndView searchUsers(@RequestParam("query") String query){
        List<User> users = userService.searchUsers(query);

        ModelAndView mv = new ModelAndView();
        mv.addObject("users",users);
        mv.setViewName("user/userManagement");

        return mv;
    }

    @GetMapping("/manageUsers")
    public String manageUsers(){
        return "user/manageUsers";
    }

    @GetMapping("/userCreation")
    public String userCreation(){
        return "user/userCreation";
    }

    @GetMapping("/userDeletion")
    public String userDeletion(){
        return "user/userDeletion";
    }

    @GetMapping("/userGet")
    public String userGet(){
        return "user/userGet";
    }

    @GetMapping("/allusers")
    public String allusers(){
        return "user/userManagement";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "user/register";
    }

    @GetMapping("/userUpdate")
    public String userUpdate(@RequestParam("userID") int userID, Model model){
        model.addAttribute("userID",userID);
        return "user/userUpdate";
    }
}
