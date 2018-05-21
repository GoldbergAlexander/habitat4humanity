package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.RoleDAO;
import com.agoldberg.hercules.dao.UserDAO;
import com.agoldberg.hercules.domain.RoleDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.domain.TokenType;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.dto.RoleDTO;
import com.agoldberg.hercules.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${admin.username:admin@admin.com}")
    private String adminUsername;

    @Value("${admin.password:admin}")
    private String adminPassword;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private StoreLocationServiceImpl storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;



    public UserServiceImpl() {
        super();
    }

    protected UserDomain fetchActiveUser() {
        UserDomain user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = userDAO.findByUsername(currentUserName);
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        UserDomain userDomain = userDAO.findByUsername(s);
        if (userDomain == null) {
            throw new UsernameNotFoundException(s);
        }
        LOGGER.debug("Getting user by username: {}",  s);
        return userDomain;
    }

    @Override
    public UserDTO getCurrentUser(){
        LOGGER.debug("Getting active user");
        return modelMapper.map(fetchActiveUser(), UserDTO.class);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public List<RoleDTO> getRoleList(){
        List<RoleDomain> domains = roleDAO.findAll();
        List<RoleDTO> dtos = new ArrayList<>();
        for(RoleDomain domain: domains){
            dtos.add(modelMapper.map(domain, RoleDTO.class));
        }
        LOGGER.info("Getting roles list, size: {}", dtos.size());
        return dtos;
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public List<UserDTO> getUsers(){
        List<UserDomain> userDomains = userDAO.findAll();

        //Init DTO list
        List<UserDTO> userDTOS = new ArrayList<>();

        //Convert to DTOs
        for(UserDomain domain: userDomains){
            UserDTO dto = modelMapper.map(domain, UserDTO.class);
            userDTOS.add(dto);
        }
        LOGGER.info("Getting user list, size: {}", userDTOS.size());
        return userDTOS;
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public UserDTO getUser(Long id){
        UserDomain domain = userDAO.getOne(id);
        LOGGER.info("Getting user by ID: {}", id);
        return modelMapper.map(domain, UserDTO.class);
    }


    @Override
    public void createUser(UserDTO userDTO){
        UserDomain userDomain = modelMapper.map(userDTO, UserDomain.class);

        if(userDomain == null){
            throw new IllegalArgumentException("There was an error mapping the UserDTO to a UserDomain object.");
        }

        //Check if the user already exists
        if(userDAO.findByUsername(userDomain.getUsername()) != null){
            throw new IllegalStateException("The username already exists.");
        }

        //Secure the users password
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        userDomain.setEnabled(false);
        userDAO.save(userDomain);

        LOGGER.info("Saved User: {}", userDomain.getUsername());

        LOGGER.debug("Handing off to token service");
        tokenService.createToken(userDomain, TokenType.CONFRIM);

    }

    @Override
    public UserDTO confirmUser(String token){
        UserDomain user = tokenService.validateToken(token, TokenType.CONFRIM);
        if(user == null){
            throw new IllegalArgumentException("The token provided is not valid.");
        }

        user.setEnabled(true);
        user = userDAO.save(user);
        LOGGER.info("Enabled User: {}", user.getUsername());
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void passwordResetSetup(String email){
        LOGGER.info("Checking for user with email: {}", email);
        UserDomain user = userDAO.findByUsername(email);
        if(user == null){
            LOGGER.info("User not found");
            return;
        }
        LOGGER.info("User Found, handing off to TokenService");
        tokenService.createToken(user, TokenType.RESET);
    }

    @Override
    public void resetPassword(String token){
        LOGGER.info("Checking for user with token: {}", token);
        UserDomain user = tokenService.validateToken(token, TokenType.RESET);
        if(user == null){
            throw new IllegalArgumentException("The token provided is not valid.");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                //Add a chance password privilege
                new SimpleGrantedAuthority("PRIVILEGE_CHANGE_PASSWORD")));


        //Set the new user to the user context
        SecurityContextHolder.getContext().setAuthentication(auth);
        LOGGER.info("Rest User Password: {}", user.getUsername());
    }

    @Override
    public void changeCurrentUserPassword(String newPassword){
        LOGGER.info("Changing password for logged in user");
        String encodedPassword = passwordEncoder.encode(newPassword);
        UserDomain userDomain = fetchActiveUser();
        userDomain.setPassword(encodedPassword);
        userDAO.save(userDomain);
    }

    @Override
    public void userUpdateUser(UserDTO dto){
        LOGGER.info("Updating user as user: {}", dto.getUsername());
        /** Not using model mapper to avoid setting protected values **/
        UserDomain existingUserDomain = userDAO.getOne(dto.getId());

        existingUserDomain.setFirstName(dto.getFirstName());
        existingUserDomain.setLastName(dto.getLastName());

        StoreLocationDomain storeLocationDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        existingUserDomain.setLocation(storeLocationDomain);

        userDAO.save(existingUserDomain);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public void adminUpdateUser(UserDTO dto){
        LOGGER.info("Updating user as admin: {}", dto.getUsername());
        //Do everything the user can do
        userUpdateUser(dto);
        UserDomain existingUserDomain = userDAO.getOne(dto.getId());
        existingUserDomain.setUsername(dto.getUsername());

        if(dto.getRolesId() != null) {
            RoleDomain roleDomain = roleDAO.getOne(dto.getRolesId());
            existingUserDomain.setRoles(roleDomain);
        }
        userDAO.save(existingUserDomain);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public void toggleLock(Long id){
       UserDomain user = userDAO.getOne(id);
       user.setAccountNonLocked(!user.isAccountNonLocked());
       userDAO.save(user);
       LOGGER.info("Setting user: {} to enabled: {}", user.getUsername(),user.isEnabled());
    }

    public void createAdmin(){
        UserDomain userDomain = new UserDomain();
        userDomain.setEnabled(true);
        userDomain.setAccountNonLocked(true);
        userDomain.setPassword(passwordEncoder.encode(adminPassword));
        userDomain.setUsername(adminUsername);

        /** Find Admin Role **/
        RoleDomain adminRole = roleDAO.findByName("ROLE_ADMIN");
        userDomain.setRoles(adminRole);

        userDAO.save(userDomain);

        LOGGER.info("Created Admin User");
    }
}
