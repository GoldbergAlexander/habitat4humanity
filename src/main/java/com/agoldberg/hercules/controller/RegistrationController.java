package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.RegistrationDTO;
import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@Profile("!emailless")
public class RegistrationController {

    private static final String REGISTER_MODEL = "register";
    private static final String LOCATIONS_MODEL = "locations";
    private static final String USER_MODEL = "user";
    private static final String REGISTRATION_FORM_VIEW = "registration/RegistrationForm";
    private static final String REGISTRATION_CONFIRMED_VIEW = "registration/UserConfirmed";
    private static final String REGISTRATION_SUCCESS_VIEW = "registration/RegistrationSuccess";

    @Value("${system.use.email:true}")
    private boolean useEmail;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showRegistrationForm(Model model){
        return new ModelAndView(REGISTRATION_FORM_VIEW, REGISTER_MODEL, new RegistrationDTO());
    }


    @PostMapping
    public ModelAndView handleRegistration(Model model, @Valid @ModelAttribute(REGISTER_MODEL) RegistrationDTO registration, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            return new ModelAndView(REGISTRATION_SUCCESS_VIEW, REGISTER_MODEL, registration);
        }else{
            return new ModelAndView(REGISTRATION_FORM_VIEW, REGISTER_MODEL, registration);
        }
    }

    @GetMapping("confirm")
    public ModelAndView handleConfirmation(@RequestParam("token") String uuid){
        UserDTO userDTO = userService.confirmUser(uuid);
        return new ModelAndView(REGISTRATION_CONFIRMED_VIEW, USER_MODEL, userDTO);
    }

}
