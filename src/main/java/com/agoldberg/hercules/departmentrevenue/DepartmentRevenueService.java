package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.department.DepartmentService;
import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentRevenueService {
    @Autowired
    private DepartmentRevenueDAO dao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentRevenueService.class);

    public DepartmentRevenueDTO createDepartmentRevenueEntry(DepartmentRevenueDTO dto){
        StoreDomain store = storeService.getStore(dto.getStoreId());
        DepartmentDomain department = departmentService.getDepartment(dto.getDepartmentId());

        //Validate and modify date
        Date date = dto.getDate();
        date = normalizeDate(date);

        DepartmentRevenueDomain domain;
        if((domain = dao.findByStoreAndDepartmentAndDate(store, department, date)) != null){
            LOGGER.warn("Overwrite Warning: Replacing entry: {}, store: {}, department: {}, date: {}, amount: {}",
                    domain.getId(), domain.getStore().getName(), domain.getDepartment().getName(), domain.getDate().toString(), domain.getAmount());
            domain.setAmount(dto.getAmount());
        }else {
            domain = new DepartmentRevenueDomain(store, department, dto.getDate(), dto.getAmount());
        }

        domain = dao.save(domain);
        LOGGER.info("Writing Department Revenue Entry: {}, store: {}, department: {}, date: {}, amount: {}",
                domain.getId(), domain.getStore().getName(), domain.getDepartment().getName(), domain.getDate().toString(), domain.getAmount());
        return modelMapper.map(domain, DepartmentRevenueDTO.class);
    }

    public List<DepartmentRevenueDTO> findAll(){
        List<DepartmentRevenueDTO> dtos = new ArrayList<>();
        dao.findAll().forEach(domain -> dtos.add(modelMapper.map(domain, DepartmentRevenueDTO.class)));
        LOGGER.info("Returning all department revenue entries, size{}", dtos.size());
        return dtos;
    }

    private Date normalizeDate(Date date) {
        if(date == null){
            throw new IllegalArgumentException("Bad Date");
        }
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setDate(1);

        return date;
    }

}
