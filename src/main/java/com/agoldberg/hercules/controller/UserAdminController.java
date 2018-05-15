package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.RoleDTO;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StoreLocationService storeLocationService;

    @RequestMapping
    public ModelAndView showUserList(Model model){
        List<UserDTO> users = userService.getUsers();
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("roles", roles);
        model.addAttribute("changeUser", new UserDTO());
        return new ModelAndView("user/UserList", "users", users);
    }

    @GetMapping("{user_id}")
    public ModelAndView showUserForm(Model model, @PathVariable("user_id") Long userId){
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("roles", roles);
        UserDTO user = userService.getUser(userId);
        return new ModelAndView("user/UserForm", "user", user);
    }

    @PostMapping("{user_id}")
    public ModelAndView modifyUser(Model model,@PathVariable("user_id") Long userId, @Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult){
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("roles", roles);
        if(!bindingResult.hasErrors()){
            userService.adminUpdateUser(user);
            return new ModelAndView("redirect:/user/admin");
        }else{
            return new ModelAndView("user/UserForm", "user", user);
        }
    }

    @PostMapping("lock")
    public String toggleEnableUser(@ModelAttribute("changeUser") UserDTO user){
        userService.toggleLock(user.getId());
        return "redirect:/user/admin";
    }

}
