package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.DepartmentDAO;
import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.DepartmentSizeDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.DepartmentDTO;
import com.agoldberg.hercules.dto.DepartmentSizeDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    private static final String MAPPING_ERROR = "Could not map the department dto to the domain object.";
    private static final String DEPARTMENT_EXISTS_ERROR = "A department that is enabled and with the ID provided could not be found.";
    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private StoreLocationServiceImpl storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentSizeDTO> getDepartmentSizes(Long departmentId){
        DepartmentDomain departmentDomain = departmentDAO.getOne(departmentId);
        List<DepartmentSizeDTO> sizeDTOS = new ArrayList<>();
        for(DepartmentSizeDomain sizeDomain : departmentDomain.getSizes()){
            sizeDTOS.add(modelMapper.map(sizeDomain, DepartmentSizeDTO.class));
        }
        return sizeDTOS;
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void createDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        domain.setLocation(storeDomain);
        domain.setEnabled(true);
        departmentDAO.save(domain);
        LOGGER.info("Created Department: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void deleteDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }
        departmentDAO.deleteById(domain.getId());
        LOGGER.info("Deleted Department: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void modifyDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        domain.setLocation(storeDomain);
        departmentDAO.save(domain);
        LOGGER.info("Modified Department: {}", domain.getName());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void toggleDepartmentEnabled(Long id){
        DepartmentDomain domain = departmentDAO.getOne(id);
        domain.setEnabled(!domain.isEnabled());
        departmentDAO.save(domain);
        LOGGER.info("Set Department: {} to enabled: {}", domain.getName(),domain.isEnabled());
    }

    @Override
    public List<DepartmentDTO> getDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }
        LOGGER.info("Getting list of departments, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public List<DepartmentDTO> getEnabledDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }

        LOGGER.info("Getting list of enabled departments, size: {}", dtos.size());
        return dtos;
    }

    @Override
    public String getDepartmentName(Long id){
        LOGGER.info("Getting department name with id: {}", id);
        return getDepartment(id).getName();
    }

    @Override
    public DepartmentDTO getDepartmentDTO(Long id){
        LOGGER.info("Getting department DTO with id: {}", id);
        return modelMapper.map(departmentDAO.getOne(id), DepartmentDTO.class);
    }

    protected DepartmentDomain getDepartment(Long id){
        DepartmentDomain domain = departmentDAO.findByIdAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalStateException(DEPARTMENT_EXISTS_ERROR);
        }
        LOGGER.info("Getting department domain with id: {}", id);
        return domain;
    }

}
