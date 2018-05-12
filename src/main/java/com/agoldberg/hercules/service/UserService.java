package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.UserDAO;
import com.agoldberg.hercules.domain.TokenType;
import com.agoldberg.hercules.domain.UserDomain;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${admin.username:admin@admin.com}")
    private String adminUsername;

    @Value("${admin.password:admin}")
    private String adminPassword;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public UserService() {
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

        return userDomain;
    }

    public List<UserDTO> getUsers(){
        List<UserDomain> userDomains = userDAO.findAll();

        //Init DTO list
        List<UserDTO> userDTOS = new ArrayList<>();

        //Convert to DTOs
        for(UserDomain domain: userDomains){
            UserDTO dto = modelMapper.map(domain, UserDTO.class);
            userDTOS.add(dto);
        }

        return userDTOS;
    }

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

        LOGGER.info("Saved User: " + userDomain.getUsername());

        LOGGER.debug("Handing off to token service");
        tokenService.createToken(userDomain, TokenType.CONFRIM);

    }

    public UserDTO confirmUser(String token){
        UserDomain user = tokenService.validateToken(token, TokenType.CONFRIM);
        if(user == null){
            throw new IllegalArgumentException("The token provided is not valid.");
        }

        user.setEnabled(true);
        user = userDAO.save(user);
        LOGGER.info("Enabled User: " + user.getUsername());
        return modelMapper.map(user, UserDTO.class);
    }

    public void passwordResetSetup(String email){
        LOGGER.info("Checking for user with email: " + email);
        UserDomain user = userDAO.findByUsername(email);
        if(user == null){
            LOGGER.info("User not found");
            return;
        }
        LOGGER.info("User Found, handing off to TokenService");
        tokenService.createToken(user, TokenType.RESET);
    }

    public void resetPassword(String token){
        LOGGER.info("Checking for user with token: " + token);
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
        LOGGER.info("Rest User Password: " + user.getUsername());
    }

    public void changeCurrentUserPassword(String newPassword){
        LOGGER.info("Changing password for logged in user");
        String encodedPassword = passwordEncoder.encode(newPassword);
        UserDomain userDomain = fetchActiveUser();
        userDomain.setPassword(encodedPassword);
        userDAO.save(userDomain);
    }

    public void toggleLock(Long id){
       UserDomain user = userDAO.getOne(id);
       if(user == null){
           throw new IllegalArgumentException("Could not get a user with the ID: " + id);
       }
       user.setAccountNonLocked(!user.isAccountNonLocked());
       userDAO.save(user);
       LOGGER.info("Toggled enable state of user ");
    }

    public void createAdmin(){
        UserDomain userDomain = new UserDomain();
        userDomain.setEnabled(true);
        userDomain.setAccountNonLocked(true);
        userDomain.setPassword(passwordEncoder.encode(adminPassword));
        userDomain.setUsername(adminUsername);
        userDAO.save(userDomain);
    }
}
