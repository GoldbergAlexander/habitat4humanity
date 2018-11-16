package com.agoldberg.hercules.department;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    private static final String MAPPING_ERROR = "Could not map the department dto to the domain object.";
    private static final String EXISTS_ERROR = "A department with the provided name already exists.";

    @Autowired
    private DepartmentDAO dao;

    @Autowired
    private ModelMapper modelMapper;

    public void toggleDepartmentEnabled(Long id){
        DepartmentDomain department = dao.getOne(id);
        department.setEnabled(!department.isEnabled());
        dao.save(department);
        LOGGER.info("Department: {} enabled set to: {}", department.getName(), department.isEnabled());
    }

    public void createDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }

        if(dao.findByName(dto.getName()) != null){
            throw new IllegalStateException(EXISTS_ERROR);
        }

        domain.setEnabled(true);
        dao.save(domain);
        LOGGER.info("Created department: {}", domain.getName());
    }

    public void deleteDepartment(DepartmentDTO dto){
        DepartmentDomain domain = modelMapper.map(dto, DepartmentDomain.class);
        if(domain == null){
            throw new IllegalArgumentException(MAPPING_ERROR);
        }
        dao.deleteById(domain.getId());
        LOGGER.info("Deleted department: {}", domain.getName());
    }

    public void modifyDepartment(DepartmentDTO dto){
        DepartmentDomain domain = dao.getOne(dto.getId());
        domain.setName(dto.getName());
        domain.setDetails(dto.getDetails());
        dao.save(domain);
        LOGGER.info("Modified Department: {}", domain.getName());
    }

    public DepartmentDTO getDepartmentDTO(Long id){
        LOGGER.info("Getting department with ID: {}", id);
        return modelMapper.map(dao.getOne(id),DepartmentDTO.class);
    }

    public DepartmentDomain getDepartment(Long id){
        DepartmentDomain domain = dao.findByIdAndEnabledIsTrue(id);
        if(domain == null){
            throw new IllegalArgumentException("Department with the given ID does not exist");
        }
        LOGGER.info("Got enabled department with ID: {}", id);
        return domain;
    }

    public List<DepartmentDTO> getDepartments(){
        List<DepartmentDomain> domains = dao.findAll();
        List<DepartmentDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, DepartmentDTO.class)));
        LOGGER.info("Getting list of enabled departments, size: {}", dtos.size());
        return dtos;
    }

    public List<DepartmentDTO> getEnabledDepartments(){
        List<DepartmentDomain> domains = dao.findByEnabledIsTrue();
        List<DepartmentDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, DepartmentDTO.class)));
        LOGGER.info("Getting list of enabled departments, size: {}", dtos.size());
        return dtos;
    }

}
