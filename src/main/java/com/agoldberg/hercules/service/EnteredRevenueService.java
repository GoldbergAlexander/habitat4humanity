package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;

import javax.annotation.security.RolesAllowed;

public interface EnteredRevenueService {
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    EnteredRevenueDTO createRevenueEntry(EnteredRevenueDTO dto);
}
