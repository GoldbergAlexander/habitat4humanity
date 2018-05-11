package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.DepartmentDAO;
import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.DepartmentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private StoreLocationService storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    public void createDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        departmentDAO.save(domain);
    }

    public void deleteDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }
        departmentDAO.deleteById(domain.getId());
    }

    public void modifyDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }

        if(departmentDAO.findById(domain.getId()) == null){
            throw new IllegalStateException("A department with the ID provided could not be found.");
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        departmentDAO.save(domain);
    }

    public List<DepartmentDTO> getDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }

        return dtos;
    }

    public List<DepartmentDTO> getEnabledDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }

        return dtos;
    }

}
