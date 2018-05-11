package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.DepartmentRevenueDAO;
import com.agoldberg.hercules.domain.DepartmentDomain;
import com.agoldberg.hercules.domain.DepartmentRevenueDomain;
import com.agoldberg.hercules.dto.DepartmentRevenueDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentRevenueService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRevenueDAO departmentRevenueDAO;

    @Autowired
    private ModelMapper modelMapper;

    public DepartmentRevenueDTO createRevenueEntry(DepartmentRevenueDTO dto){
        DepartmentRevenueDomain domain = modelMapper.map(dto, DepartmentRevenueDomain.class);
        if(domain == null){
            throw new IllegalArgumentException("Could not map to entered revenue dto to a domain object.");
        }

        DepartmentDomain departmentDomain = departmentService.getDepartment(dto.getDepartmentId());
        domain.setDepartment(departmentDomain);

        domain = departmentRevenueDAO.save(domain);

        return modelMapper.map(domain, DepartmentRevenueDTO.class);
    }
}
