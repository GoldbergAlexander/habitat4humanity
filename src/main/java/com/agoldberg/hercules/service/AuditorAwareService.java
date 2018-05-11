package com.agoldberg.hercules.service;

import com.agoldberg.hercules.domain.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareService implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
