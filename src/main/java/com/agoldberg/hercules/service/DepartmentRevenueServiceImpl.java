package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.DepartmentRevenueDAO;
import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.DepartmentRevenueDomain;
import com.agoldberg.hercules.dto.DepartmentRevenueDTO;
import com.agoldberg.hercules.service.DepartmentRevenueService;
import com.agoldberg.hercules.service.DepartmentServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

@Service
public class DepartmentRevenueServiceImpl implements DepartmentRevenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentRevenueDAO departmentRevenueDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    public DepartmentRevenueDTO createRevenueEntry(DepartmentRevenueDTO dto){
        DepartmentRevenueDomain domain = modelMapper.map(dto, DepartmentRevenueDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map to entered revenue dto to a domain object.");
        }
        DepartmentDomain departmentDomain = departmentService.getDepartment(dto.getDepartmentId());
        domain.setDepartment(departmentDomain);
        domain = departmentRevenueDAO.save(domain);
        LOGGER.info("Created department revenue entry for department: " + dto.getDepartmentName());
        return modelMapper.map(domain, DepartmentRevenueDTO.class);
    }
}
