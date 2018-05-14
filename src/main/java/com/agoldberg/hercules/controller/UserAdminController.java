package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.RoleDTO;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

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
        return new ModelAndView("UserList", "users", users);
    }

    @PostMapping("modify")
    public String modifyUser(@Valid @ModelAttribute("changeUser") UserDTO user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        userService.adminUpdateUser(user);
        return "redirect:/user/admin";
    }

    @PostMapping("lock")
    public String toggleEnableUser(@ModelAttribute("changeUser") UserDTO user){
        userService.toggleLock(user.getId());
        return "redirect:/user/admin";
    }

}
