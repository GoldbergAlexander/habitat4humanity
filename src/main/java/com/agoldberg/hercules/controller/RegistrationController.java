package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.RegistrationDTO;
import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showRegistrationForm(){
        return new ModelAndView("RegistrationForm", "register", new RegistrationDTO());
    }

    @PostMapping
    public ModelAndView handleRegistration(@Valid @ModelAttribute("register") RegistrationDTO registration, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            userService.createUser(registration);
            return new ModelAndView("RegistrationSuccess", "register", registration);
        }else{
            return new ModelAndView("RegistrationForm", "register", registration);
        }
    }

    @GetMapping("confirm")
    public ModelAndView handleConfirmation(@RequestParam("token") String uuid){
        UserDTO userDTO = userService.confirmUser(uuid);
        return new ModelAndView("UserConfirmed", "user", userDTO);
    }

}
