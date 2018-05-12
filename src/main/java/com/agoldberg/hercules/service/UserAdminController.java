package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView showUserList(Model model){
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("changeUser", new UserDTO());
        return new ModelAndView("UserList", "users", users);
    }

    @PostMapping("lock")
    public String toggleEnableUser(@ModelAttribute("changeUser") UserDTO user){
        userService.toggleLock(user.getId());
        return "redirect:/user/admin";
    }

}
