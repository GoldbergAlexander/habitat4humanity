package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.StoreLocationDTO;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface StoreLocationService {
    @RolesAllowed("ROLE_ADMIN")
    void toggleStoreLocationEnabled(Long id);

    @RolesAllowed("ROLE_ADMIN")
    void createStoreLocation(StoreLocationDTO storeLocationDTO);

    @RolesAllowed("ROLE_ADMIN")
    void deleteStoreLocation(StoreLocationDTO storeLocationDTO);

    @RolesAllowed("ROLE_ADMIN")
    void modifyStoreLocation(StoreLocationDTO storeLocationDTO);

    StoreLocationDTO getStoreLocationDTO(Long id);

    List<StoreLocationDTO> getStoreLocations();

    List<StoreLocationDTO> getEnabledStoreLocations();

    String getStoreName(Long id);
}
