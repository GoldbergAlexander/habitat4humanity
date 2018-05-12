package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView showUserList(){
        List<UserDTO> users = userService.getUsers();
        return new ModelAndView("UserList", "users", users);
    }

    @GetMapping("forgot")
    public String showForgetForm(){
        return "ForgotPasswordForm";
    }

    @PostMapping("forgot")
    public String handleForgotPassword(@RequestParam("email") String email){
        userService.passwordResetSetup(email);
        return "ForgotPasswordSuccess";
    }

    @GetMapping("reset")
    public String validateResetToken(@RequestParam("token") String token){
        userService.resetPassword(token);
        return "redirect:/user/changepassword";
    }

    @GetMapping("changepassword")
    public String showPasswordResetForm(){
        return "PasswordResetForm";
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
