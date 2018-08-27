package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.PasswordChangeDTO;
import com.agoldberg.hercules.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
    public String resetPassword(@RequestParam("password") String password, @RequestParam("confirm") String confirm){
        if(password.equals(confirm)) {
            PasswordChangeDTO passwordChange = new PasswordChangeDTO();
            passwordChange.setPassword(password);
            passwordChange.setConfirmPassword(confirm);
            userService.changeCurrentUserPassword(passwordChange);
            return SUCCESS_REDIRECT;
        }
        return USER_CHANGE_PASSWORD_REDIRECT;
    }

    @GetMapping("passwordchange")
    public ModelAndView showPasswordChangeForm(){
        LOGGER.debug("Sending Password Change Form");
        return new ModelAndView("user/ChangePassword","change",new PasswordChangeDTO());
    }

    @PostMapping("passwordchange")
    public ModelAndView changePassword(@Valid @ModelAttribute("change") PasswordChangeDTO passwordChange, BindingResult result){
        //TODO Validate current password
        LOGGER.debug("Got password change object");
        if(result.hasErrors()){
            LOGGER.debug("Error in password change object, sending back.");
            return new ModelAndView("user/ChangePassword","change",passwordChange);
        }else{
            LOGGER.debug("Password change object is valid, sending to user service.");
            userService.changeCurrentUserPassword(passwordChange);
            LOGGER.debug("Redirecting to SUCCESS");
            return new ModelAndView(SUCCESS_REDIRECT);
        }
    }





}
