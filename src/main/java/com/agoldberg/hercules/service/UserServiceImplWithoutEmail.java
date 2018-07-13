package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.UserDAO;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This implementation extends and overrides the standard implementation when emailless mode is enabled.
 */

@Service
@Profile("emailless")
public class UserServiceImplWithoutEmail extends UserServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplWithoutEmail.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDTO createUser(UserDTO userDTO){
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

        userDomain.setEnabled(true);

        UserDTO dto = modelMapper.map(userDAO.save(userDomain),UserDTO.class);

        LOGGER.info("Saved User: {}", userDomain.getUsername());

        return dto;

    }

    @Override
    public UserDTO confirmUser(String token){
       throw new UnsupportedOperationException();
    }

    @Override
    public void passwordResetSetup(String email){
        throw new UnsupportedOperationException();
    }

    @Override
    public void resetPassword(String token){
        throw new UnsupportedOperationException();
    }

}
