package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.EnteredRevenueDAO;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import com.agoldberg.hercules.event.RevenueEnteredEvent;
import com.agoldberg.hercules.store.StoreServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnteredRevenueServiceImpl implements EnteredRevenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnteredRevenueService.class);
    private static final String MAPPING_ERROR = "Could not map to entered revenue dto to a domain object.";

    @Autowired
    private EnteredRevenueDAO enteredRevenueDAO;

    @Autowired
    private StoreServiceImpl storeLocationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    public EnteredRevenueDTO checkForExistingEntry(EnteredRevenueDTO dto) {
        LOGGER.info("Checking for existing revenue entry");
        EnteredRevenueDomain domain = modelMapper.map(dto, EnteredRevenueDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        //Convert the store location id to a domain object
        StoreDomain storeLocationDomain = storeLocationService.getStore(dto.getLocationId());

        //Set the domain object inside the domain
        domain.setLocation(storeLocationDomain);

        //Locate existing revenue entry for the same date and location
        //There can already only be one because we have a table constraint
        EnteredRevenueDomain existing = enteredRevenueDAO.findByLocationAndDate(domain.getLocation(), domain.getDate());

        //There is no existing, so return null

        if(existing == null){
            return null;
        }
        LOGGER.info("Found existing revenue entry for location: {} and date: {}", domain.getLocation().getName(), domain.getDate());
        //Return a DTO of the existing data.
        return modelMapper.map(existing, EnteredRevenueDTO.class);
    }

    @Override
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    public EnteredRevenueDTO createRevenueEntry(EnteredRevenueDTO dto){
        EnteredRevenueDomain domain = modelMapper.map(dto, EnteredRevenueDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        //Convert the store location id to a domain object
        StoreDomain storeLocationDomain = storeLocationService.getStore(dto.getLocationId());

        //Set the domain object inside the domain
        domain.setLocation(storeLocationDomain);

        //Locate existing revenue entry for the same date and location
        //There can already only be one because we have a table constraint
        EnteredRevenueDomain existing = enteredRevenueDAO.findByLocationAndDate(domain.getLocation(), domain.getDate());
        //If the existing isn't null, then set the new to the domain id and continue as normal
        if(existing != null){
            domain.setId(existing.getId());
            //Maintain the audit logs
            domain.setCreatedBy(existing.getCreatedBy());
            domain.setCreationDate(existing.getCreationDate());
            LOGGER.warn("Entered Revenue Data is being overwritten by user: {}", SecurityContextHolder.getContext().getAuthentication().getName());

        }

        //Return the saved info to get the details
        domain = enteredRevenueDAO.save(domain);

        //Create event
        LOGGER.debug("Published entered revenue event");
        RevenueEnteredEvent event = new RevenueEnteredEvent(this, domain);
        eventPublisher.publishEvent(event);

        LOGGER.info("Created or updated entered revenue entry for location: {} and date: {}", domain.getLocation().getName(),domain.getDate());
        return modelMapper.map(domain, EnteredRevenueDTO.class);
    }

    @Override
    public List<EnteredRevenueDTO> createRevenueEntry(List<EnteredRevenueDTO> dtos) {
        List<EnteredRevenueDTO> savedDTO = new ArrayList<>();
        for(EnteredRevenueDTO dto: dtos){
            savedDTO.add(createRevenueEntry(dto));
        }
        return savedDTO;
    }
}
