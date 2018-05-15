package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.StoreLocationDAO;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {
    @Autowired
    private StoreLocationDAO storeLocationDAO;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void toggleStoreLocationEnabled(Long id){
        StoreLocationDomain location = storeLocationDAO.getOne(id);
        location.setEnabled(!location.isEnabled());
        storeLocationDAO.save(location);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void createStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain storeLocationDomain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);

        if(storeLocationDomain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }

        if(storeLocationDAO.findByName(storeLocationDomain.getName()) != null){
            throw new IllegalStateException("A store location with the provided name already exists.");
        }

        storeLocationDomain.setEnabled(true);

        storeLocationDAO.save(storeLocationDomain);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void deleteStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }
        storeLocationDAO.deleteById(domain.getId());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void modifyStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }

        storeLocationDAO.save(domain);
    }

    @Override
    public StoreLocationDTO getStoreLocationDTO(Long id){
        return modelMapper.map(storeLocationDAO.getOne(id), StoreLocationDTO.class);
    }

    protected StoreLocationDomain getStoreLocation(Long id){
        StoreLocationDomain storeLocation = storeLocationDAO.findByIdAndAndEnabledIsTrue(id);
        if(storeLocation == null){
            throw new IllegalStateException("A store location that is enabled and with the ID provided could not be found.");
        }
        return storeLocation;
    }

    @Override
    public List<StoreLocationDTO> getStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findAll();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }

        return dtos;
    }

    @Override
    public List<StoreLocationDTO> getEnabledStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findByEnabledIsTrue();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }

        return dtos;
    }

    @Override
    public String getStoreName(Long id){
        StoreLocationDomain domain = storeLocationDAO.findByIdAndAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalStateException("A store location that is enabled and with the ID provided could not be found.");
        }
        return domain.getName();
    }
}
