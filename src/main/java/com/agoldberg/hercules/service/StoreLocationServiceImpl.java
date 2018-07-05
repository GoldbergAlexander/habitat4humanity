package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.StoreLocationDAO;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreLocationService.class);
    private static final String MAPPING_ERROR = "Could not map the store location dto to the domain object.";
    private static final String LOCATION_EXISTS_ERROR = "A store location with the provided name already exists.";

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
        LOGGER.info("Location: {} enabled set to: {}", location.getName(), location.isEnabled());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void createStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain storeLocationDomain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);

        if(storeLocationDomain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        if(storeLocationDAO.findByName(storeLocationDomain.getName()) != null){
            throw new IllegalStateException(LOCATION_EXISTS_ERROR);
        }

        storeLocationDomain.setEnabled(true);

        storeLocationDAO.save(storeLocationDomain);
        LOGGER.info("Created location: {}", storeLocationDomain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void deleteStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }
        storeLocationDAO.deleteById(domain.getId());
        LOGGER.info("Deleted location: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void modifyStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        storeLocationDAO.save(domain);
        LOGGER.info("Modified location: {}", domain.getName());
    }

    @Override
    public StoreLocationDTO getStoreLocationDTO(Long id){
        LOGGER.info("Getting location with ID: {}", id);
        return modelMapper.map(storeLocationDAO.getOne(id), StoreLocationDTO.class);
    }

    protected StoreLocationDomain getStoreLocation(Long id){
        StoreLocationDomain storeLocation = storeLocationDAO.findByIdAndEnabledIsTrue(id);
        if(storeLocation == null){
            throw new IllegalStateException("Location with the given ID does not exist");
        }
        LOGGER.info("Got enabled location with ID: {}", id);
        return storeLocation;
    }

    @Override
    public List<StoreLocationDTO> getStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findAll();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }

        LOGGER.info("Getting list of locations, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public List<StoreLocationDTO> getEnabledStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findByEnabledIsTrue();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }
        LOGGER.info("Getting list of enabled locations, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public String getStoreName(Long id){
        StoreLocationDomain domain = storeLocationDAO.findByIdAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalStateException(LOCATION_EXISTS_ERROR);
        }
        LOGGER.info("Getting location domain with ID: {}", id);
        return domain.getName();
    }
}
