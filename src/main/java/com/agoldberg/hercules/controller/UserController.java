package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
