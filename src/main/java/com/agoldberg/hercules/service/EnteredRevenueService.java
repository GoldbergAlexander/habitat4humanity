package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.EnteredRevenueDAO;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnteredRevenueService {

    @Autowired
    private EnteredRevenueDAO enteredRevenueDAO;

    @Autowired
    private StoreLocationService storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    public EnteredRevenueDTO createRevenueEntry(EnteredRevenueDTO dto){
        EnteredRevenueDomain domain = modelMapper.map(dto, EnteredRevenueDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map to entered revenue dto to a domain object.");
        }

        //Convert the store location id to a domain object;
        StoreLocationDomain storeLocationDomain = storeLocationService.getStoreLocation(dto.getLocationId());

        //Set the domain object inside the domain
        domain.setLocation(storeLocationDomain);

        //Return the saved info to get the details
        domain = enteredRevenueDAO.save(domain);
        return modelMapper.map(domain, EnteredRevenueDTO.class);
    }
}
