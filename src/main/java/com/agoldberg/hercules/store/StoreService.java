package com.agoldberg.hercules.store;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface StoreService {
    @RolesAllowed("ROLE_ADMIN")
    void toggleStoreEnabled(Long id);

    @RolesAllowed("ROLE_ADMIN")
    void createStore(StoreDTO storeLocationDTO);

    @RolesAllowed("ROLE_ADMIN")
    void deleteStore(StoreDTO storeLocationDTO);

    @RolesAllowed("ROLE_ADMIN")
    void modifyStore(StoreDTO storeLocationDTO);

    StoreDTO getStoreDTO(Long id);

    Store getStore(Long id);

    List<StoreDTO> getStores();

    List<StoreDTO> getEnabledStores();

    String getStoreName(Long id);
}
