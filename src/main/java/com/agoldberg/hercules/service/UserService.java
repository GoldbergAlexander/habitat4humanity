package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.UserDAO;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
        super();
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

    public void registerUser(UserDTO userDTO){
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

        userDAO.save(userDomain);
    }
}
