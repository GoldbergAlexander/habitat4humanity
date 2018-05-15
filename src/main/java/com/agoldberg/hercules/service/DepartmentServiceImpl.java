package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.DepartmentDAO;
import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.DepartmentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private StoreLocationServiceImpl storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void createDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        domain.setLocation(storeDomain);
        domain.setEnabled(true);
        departmentDAO.save(domain);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void deleteDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }
        departmentDAO.deleteById(domain.getId());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void modifyDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);

        if(domain == null){
            throw new IllegalArgumentException("Could not map the department dto to the domain object.");
        }

        //Will throw an exception if its not found
        StoreLocationDomain storeDomain = storeLocationService.getStoreLocation(dto.getLocationId());
        domain.setLocation(storeDomain);
        departmentDAO.save(domain);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public void toggleDepartmentEnabled(Long id){
        DepartmentDomain domain = departmentDAO.getOne(id);
        domain.setEnabled(!domain.isEnabled());
        departmentDAO.save(domain);
    }

    @Override
    public List<DepartmentDTO> getDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }

        return dtos;
    }

    @Override
    public List<DepartmentDTO> getEnabledDepartments(){
        List<DepartmentDomain> domains = departmentDAO.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();

        for(DepartmentDomain domain : domains){
            dtos.add(modelMapper.map(domain, DepartmentDTO.class));
        }

        return dtos;
    }

    @Override
    public String getDepartmentName(Long id){
        return getDepartment(id).getName();
    }

    @Override
    public DepartmentDTO getDepartmentDTO(Long id){
        return modelMapper.map(departmentDAO.getOne(id), DepartmentDTO.class);
    }

    protected DepartmentDomain getDepartment(Long id){
        DepartmentDomain domain = departmentDAO.findByIdAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalStateException("A department that is enabled and with the ID provided could not be found.");
        }
        return domain;
    }

}
