package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.StoreLocationDAO;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreLocationService {
    @Autowired
    private StoreLocationDAO storeLocationDAO;

    @Autowired
    private ModelMapper modelMapper;

    public void createStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain storeLocationDomain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);

        if(storeLocationDomain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }

        if(storeLocationDAO.findByName(storeLocationDomain.getName()) != null){
            throw new IllegalStateException("A store location with the provided name already exists.");
        }

        storeLocationDAO.save(storeLocationDomain);
    }

    public void deleteStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }
        if(storeLocationDAO.findById(domain.getId()) == null){
            throw new IllegalStateException("A store location with the ID provided could not be found.");
        }
        storeLocationDAO.deleteById(domain.getId());
    }

    public void modifyStoreLocation(StoreLocationDTO storeLocationDTO){
        StoreLocationDomain domain = modelMapper.map(storeLocationDTO, StoreLocationDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map the store location dto to the domain object.");
        }
        if(storeLocationDAO.findById(domain.getId()) == null){
            throw new IllegalStateException("A store location with the ID provided could not be found.");
        }

        storeLocationDAO.save(domain);
    }

    public List<StoreLocationDTO> getStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findAll();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }

        return dtos;
    }

    public List<StoreLocationDTO> getEnabledStoreLocations(){
        List<StoreLocationDomain> domains = storeLocationDAO.findByEnabledIsTrue();
        List<StoreLocationDTO> dtos = new ArrayList<>();

        for(StoreLocationDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreLocationDTO.class));
        }

        return dtos;
    }
}
