package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.RoleDTO;
import com.agoldberg.hercules.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface UserService {

    UserDTO getCurrentUser();

    @RolesAllowed({"ROLE_ADMIN"})
    List<RoleDTO> getRoleList();

    @RolesAllowed({"ROLE_ADMIN"})
    List<UserDTO> getUsers();

    @RolesAllowed({"ROLE_ADMIN"})
    UserDTO getUser(Long id);

    void createUser(UserDTO userDTO);

    UserDTO confirmUser(String token);

    void passwordResetSetup(String email);

    void resetPassword(String token);

    void changeCurrentUserPassword(String newPassword);

    void userUpdateUser(UserDTO dto);
}
