package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;

import javax.annotation.security.RolesAllowed;

public interface EnteredRevenueService {


    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    EnteredRevenueDTO createRevenueEntry(EnteredRevenueDTO dto);

    /** This method is a data leak if we don't want managers to be able to view data
     * Convert to a boolean to maintain integrity if desired.
     * @param dto
     * @return
     */
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    EnteredRevenueDTO checkForExistingEntry(EnteredRevenueDTO dto);
}
