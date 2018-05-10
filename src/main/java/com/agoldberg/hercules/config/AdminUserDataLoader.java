package com.agoldberg.hercules.config;

import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AdminUserDataLoader implements ApplicationListener<ContextRefreshedEvent>{
    private boolean loaded = false;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            if (!loaded) {
                UserDTO admin = new UserDTO();
                admin.setUsername("admin@admin.com");
                admin.setPassword("admin");
                userService.registerUser(admin);
                loaded = true;
            }
        } catch (Exception e){

        }

    }
}
