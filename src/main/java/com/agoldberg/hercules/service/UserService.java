package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.RoleDTO;
import com.agoldberg.hercules.dto.UserDTO;

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

    UserDTO createUser(UserDTO userDTO);

    UserDTO confirmUser(String token);

    void passwordResetSetup(String email);

    void resetPassword(String token);

    void changeCurrentUserPassword(String newPassword);

    void userUpdateUser(UserDTO dto);

    void createAdmin();

    void adminUpdateUser(UserDTO dto);

    void toggleLock(Long id);
}
