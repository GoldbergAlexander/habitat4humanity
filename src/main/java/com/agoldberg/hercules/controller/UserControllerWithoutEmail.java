package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.PasswordChangeDTO;
import com.agoldberg.hercules.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@Profile("emailless")
public class UserControllerWithoutEmail extends UserController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerWithoutEmail.class);

    public static final String USER_FORGOT_PASSWORD_FORM = "user/ForgotPasswordFormWithoutEmail";


    @GetMapping("forgot")
    public String showForgetForm(){
        return USER_FORGOT_PASSWORD_FORM;
    }

    @PostMapping("forgot")
    public String handleForgotPassword(@RequestParam("email") String email){
        throw new UnsupportedOperationException("Not supported while running in 'emailless' mode");
    }

}
