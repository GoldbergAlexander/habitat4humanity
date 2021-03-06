package com.agoldberg.hercules.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareService implements AuditorAware<String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = "Not Found";
        try {
             username = SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (NullPointerException e){
            LOGGER.warn("Could not get current user for audit.");
        }
        LOGGER.debug("Using user: {} for audit.", username);
        return Optional.of(username);
    }
}
