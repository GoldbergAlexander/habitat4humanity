package com.agoldberg.hercules.config;

import com.agoldberg.hercules.dao.RoleDAO;
import com.agoldberg.hercules.domain.RoleDomain;
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


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


            if (!loaded) {
                /** Create Roles **/
                if(roleDAO.findByName("ROLE_ADMIN") == null) {
                    RoleDomain adminRole = new RoleDomain("ROLE_ADMIN");
                    roleDAO.save(adminRole);
                }

                if(roleDAO.findByName("ROLE_MANAGER") == null) {
                    RoleDomain managerRole = new RoleDomain("ROLE_MANAGER");
                    roleDAO.save(managerRole);
                }

                if(roleDAO.findByName("ROLE_EXECUTIVE") == null) {
                    RoleDomain executiveRole = new RoleDomain("ROLE_EXECUTIVE");
                    roleDAO.save(executiveRole);
                }

                userService.createAdmin();

                loaded = true;


            }

    }
}
