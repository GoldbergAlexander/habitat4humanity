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
import java.time.Month;
import java.time.Year;
import java.util.Date;

@Service
public class DepartmentRevenueServiceImpl implements DepartmentRevenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);
    public static final String MAPPING_ERROR = "Could not map to entered revenue dto to a domain object.";

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
            throw new IllegalArgumentException(MAPPING_ERROR);
        }
        /* Map year and month to a date object  -- Use the first of the month*/
        Date date = new Date();
        date.setYear(dto.getYear().getValue());
        //Subtract 1 from Month value becouse java.time.Month [1,12] and java.util.date [0,11]
        date.setMonth(dto.getMonth().getValue()-1);

        //Put the date into the domain
        domain.setDate(date);

        DepartmentDomain departmentDomain = departmentService.getDepartment(dto.getDepartmentId());
        domain.setDepartment(departmentDomain);
        domain = departmentRevenueDAO.save(domain);
        LOGGER.info("Created department revenue entry for department: {}", dto.getDepartmentName());
        DepartmentRevenueDTO returnDTO  = modelMapper.map(domain, DepartmentRevenueDTO.class);

        //Add 1 to Month int for same reason as above
        dto.setMonth(Month.of(domain.getDate().getMonth()+1));
        dto.setYear(Year.of(date.getYear()));
        return dto;
    }
}
