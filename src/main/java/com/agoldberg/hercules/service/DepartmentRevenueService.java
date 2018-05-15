package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.DepartmentRevenueDTO;

import javax.annotation.security.RolesAllowed;

public interface DepartmentRevenueService {
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    DepartmentRevenueDTO createRevenueEntry(DepartmentRevenueDTO dto);
}
