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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    private static final String LOCATIONS_MODEL = "locations";
    private static final String ROLES_MODEL = "roles";
    private static final String USERS_MODEL = "users";
    private static final String CHANGE_USER_MODEL = "changeUser";
    private static final String USER_MODEL = "user";
    private static final String USER_FORM_VIEW = "user/UserForm";
    private static final String USER_LIST_VIEW = "user/UserList";
    private static final String USER_ADMIN_REDIRECT = "redirect:/user/admin";


    @Autowired
    private UserService userService;

    @Autowired
    private StoreLocationService storeLocationService;

    @RequestMapping
    public ModelAndView showUserList(Model model){
        List<UserDTO> users = userService.getUsers();
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute(LOCATIONS_MODEL, locations);
        model.addAttribute(ROLES_MODEL, roles);
        model.addAttribute(CHANGE_USER_MODEL, new UserDTO());
        return new ModelAndView(USER_LIST_VIEW, USERS_MODEL, users);
    }

    @GetMapping("{user_id}")
    public ModelAndView showUserForm(Model model, @PathVariable("user_id") Long userId){
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute(LOCATIONS_MODEL, locations);
        model.addAttribute(ROLES_MODEL, roles);
        UserDTO user = userService.getUser(userId);
        return new ModelAndView(USER_FORM_VIEW, USER_MODEL, user);
    }

    @PostMapping("{user_id}")
    public ModelAndView modifyUser(Model model, @PathVariable("user_id") Long userId, @Valid @ModelAttribute(USER_MODEL) UserDTO user, BindingResult bindingResult){
        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute(LOCATIONS_MODEL, locations);
        model.addAttribute(ROLES_MODEL, roles);
        if(!bindingResult.hasErrors()){
            userService.adminUpdateUser(user);
            return new ModelAndView(USER_ADMIN_REDIRECT);
        }else{
            return new ModelAndView(USER_FORM_VIEW, USER_MODEL, user);
        }
    }

    @GetMapping("{user_id}/reset")
    public ModelAndView generateNewPassword(Model model, @PathVariable("user_id") Long userId){
        UserDTO user = userService.getUser(userId);
        String generatedString = RandomStringUtils.randomAlphabetic(12);
        user.setPassword(generatedString);

        userService.adminUpdateUser(user);

        List<RoleDTO> roles = userService.getRoleList();
        List<StoreLocationDTO> locations = storeLocationService.getEnabledStoreLocations();
        model.addAttribute(LOCATIONS_MODEL, locations);
        model.addAttribute(ROLES_MODEL, roles);

        return new ModelAndView(USER_FORM_VIEW, USER_MODEL, user);

    }

    @PostMapping("lock")
    public String toggleEnableUser(@ModelAttribute(CHANGE_USER_MODEL) UserDTO user){
        userService.toggleLock(user.getId());
        return USER_ADMIN_REDIRECT;
    }

}
