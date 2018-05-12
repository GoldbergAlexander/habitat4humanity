package com.agoldberg.hercules.config;

import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.dto.UserDTO;
import com.agoldberg.hercules.service.StoreLocationService;
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

    @Autowired
    private StoreLocationService storeLocationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


            if (!loaded) {
                UserDTO admin = new UserDTO();
                admin.setUsername("admin@admin.com");
                admin.setPassword("admin");
                userService.registerUser(admin);

                StoreLocationDTO location = new StoreLocationDTO();
                location.setName("Test 1");
                location.setEnabled(true);
                storeLocationService.createStoreLocation(location);

                StoreLocationDTO location1 = new StoreLocationDTO();
                location1.setName("Test 2");
                location1.setEnabled(true);
                storeLocationService.createStoreLocation(location1);

                loaded = true;


            }

    }
}
