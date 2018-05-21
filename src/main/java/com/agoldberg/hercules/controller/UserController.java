package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String USER_INFO_VIEW = "user/UserInfo";
    private static final String USER_MODEL = "user";
    private static final String USER_FORGOT_PASSWORD_SUCCESS_VIEW = "user/ForgotPasswordSuccess";
    private static final String USER_CHANGE_PASSWORD_REDIRECT = "redirect:/user/changepassword";
    private static final String USER_PASSWORD_RESET_VIEW = "user/PasswordResetForm";
    public static final String SUCCESS_REDIRECT = "redirect:/";
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showUser(){
        return new ModelAndView(USER_INFO_VIEW, USER_MODEL, userService.getCurrentUser());
    }


    @GetMapping("forgot")
    public String showForgetForm(){
        return "user/ForgotPasswordForm";
    }

    @PostMapping("forgot")
    public String handleForgotPassword(@RequestParam("email") String email){
        userService.passwordResetSetup(email);
        return USER_FORGOT_PASSWORD_SUCCESS_VIEW;
    }

    @GetMapping("reset")
    public String validateResetToken(@RequestParam("token") String token){
        userService.resetPassword(token);
        return USER_CHANGE_PASSWORD_REDIRECT;
    }

    @GetMapping("changepassword")
    public String showPasswordResetForm(){
        return USER_PASSWORD_RESET_VIEW;
    }

    @PostMapping("changepassword")
    public String changePassword(@RequestParam("password") String password, @RequestParam("confirm") String confirm){
        if(password.equals(confirm)) {
            userService.changeCurrentUserPassword(password);
            return SUCCESS_REDIRECT;
        }
        return USER_CHANGE_PASSWORD_REDIRECT;
    }




}
