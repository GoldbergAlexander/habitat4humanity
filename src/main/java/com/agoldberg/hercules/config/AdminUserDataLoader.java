package com.agoldberg.hercules.config;

import com.agoldberg.hercules.dao.RoleDAO;
import com.agoldberg.hercules.domain.RoleDomain;
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
    private RoleDAO roleDAO;

    @Autowired
    private StoreLocationService storeLocationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


            if (!loaded) {
                /** Create Roles **/
                RoleDomain adminRole = new RoleDomain("ROLE_ADMIN");
                roleDAO.save(adminRole);

                RoleDomain managerRole = new RoleDomain("ROLE_MANAGER");
                roleDAO.save(managerRole);

                RoleDomain executiveRole = new RoleDomain("ROLE_EXECUTIVE");
                roleDAO.save(executiveRole);

                userService.createAdmin();

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
