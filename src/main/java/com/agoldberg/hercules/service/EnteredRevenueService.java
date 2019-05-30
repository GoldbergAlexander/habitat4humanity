package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import java.util.List;

import javax.annotation.security.RolesAllowed;

public interface EnteredRevenueService {


    EnteredRevenueDTO createRevenueEntry(EnteredRevenueDTO dto);

    List<EnteredRevenueDTO> createRevenueEntry(List<EnteredRevenueDTO> dtos);

    /** This method is a data leak if we don't want managers to be able to view data
     * Convert to a boolean to maintain integrity if desired.
     * @param dto
     * @return
     */

    EnteredRevenueDTO checkForExistingEntry(EnteredRevenueDTO dto);
}
