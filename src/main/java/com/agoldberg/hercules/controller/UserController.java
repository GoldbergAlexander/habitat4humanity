package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showUser(){
        return new ModelAndView("user/UserInfo", "user", userService.getCurrentUser());
    }


    @GetMapping("forgot")
    public String showForgetForm(){
        return "user/ForgotPasswordForm";
    }

    @PostMapping("forgot")
    public String handleForgotPassword(@RequestParam("email") String email){
        userService.passwordResetSetup(email);
        return "user/ForgotPasswordSuccess";
    }

    @GetMapping("reset")
    public String validateResetToken(@RequestParam("token") String token){
        userService.resetPassword(token);
        return "redirect:/user/changepassword";
    }

    @GetMapping("changepassword")
    public String showPasswordResetForm(){
        return "user/PasswordResetForm";
    }

    @PostMapping("changepassword")
    public String changePassword(@RequestParam("password") String password, @RequestParam("confirm") String confirm){
        if(password.equals(confirm)) {
            userService.changeCurrentUserPassword(password);
            return "redirect:/";
        }
        return "redirect:/user/changepassword";
    }




}
