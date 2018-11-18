package com.agoldberg.hercules.store;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);
    private static final String MAPPING_ERROR = "Could not map the store location dto to the domain object.";
    private static final String EXISTS_ERROR = "A store location with the provided name already exists.";

    @Autowired
    private StoreDAO dao;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void toggleStoreEnabled(Long id){
        StoreDomain location = dao.getOne(id);
        location.setEnabled(!location.isEnabled());
        dao.save(location);
        LOGGER.info("Location: {} enabled set to: {}", location.getName(), location.isEnabled());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void createStore(StoreDTO dto){
        StoreDomain domain = modelMapper.map(dto, StoreDomain.class);

        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        if(dao.findByName(domain.getName()) != null){
            throw new IllegalStateException(EXISTS_ERROR);
        }

        domain.setEnabled(true);

        dao.save(domain);
        LOGGER.info("Created location: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void deleteStore(StoreDTO dto){
        StoreDomain domain = modelMapper.map(dto, StoreDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }
        dao.deleteById(domain.getId());
        LOGGER.info("Deleted location: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void modifyStore(StoreDTO dto){
        StoreDomain domain = dao.getOne(dto.getId());

        domain.setName(dto.getName());
        domain.setCity(dto.getCity());
        domain.setLineOne(dto.getLineOne());
        domain.setLineTwo(dto.getLineTwo());
        domain.setState(dto.getState());
        domain.setZip(dto.getZip());

        dao.save(domain);
        LOGGER.info("Modified location: {}", domain.getName());
    }

    @Override
    public StoreDTO getStoreDTO(Long id){
        LOGGER.info("Getting location with ID: {}", id);
        return modelMapper.map(dao.getOne(id), StoreDTO.class);
    }

    @Override
    public StoreDomain getStore(Long id){
        return dao.getOne(id);
    }

    @Override
    public List<StoreDTO> getStores(){
        List<StoreDomain> domains = dao.findAll();
        List<StoreDTO> dtos = new ArrayList<>();

        for(StoreDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreDTO.class));
        }

        LOGGER.info("Getting list of locations, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public List<StoreDTO> getEnabledStores(){
        List<StoreDomain> domains = dao.findByEnabledIsTrue();
        List<StoreDTO> dtos = new ArrayList<>();

        for(StoreDomain domain : domains){
            dtos.add(modelMapper.map(domain, StoreDTO.class));
        }
        LOGGER.info("Getting list of enabled locations, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public String getStoreName(Long id){
        StoreDomain domain = dao.findByIdAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalStateException(EXISTS_ERROR);
        }
        LOGGER.info("Getting location domain with ID: {}", id);
        return domain.getName();
    }
}
