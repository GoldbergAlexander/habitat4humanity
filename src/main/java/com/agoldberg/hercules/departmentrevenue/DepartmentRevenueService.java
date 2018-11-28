package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentDomain;
import com.agoldberg.hercules.department.DepartmentService;
import com.agoldberg.hercules.size.SizeDTO;
import com.agoldberg.hercules.size.SizeDomain;
import com.agoldberg.hercules.size.SizeService;
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
    private SizeService sizeService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentRevenueService.class);


    public List<DepartmentRevenueExtendedAnalysesDTO> searchDepartmentEntries(SearchDTO dto){
        List<DepartmentRevenueDomain> domains = null;
        StoreDomain store = null;
        DepartmentDomain department = null;

        if(dto.getDepartmentId() != null && dto.getDepartmentId() == (-1)){
            dto.setDepartmentId(null);
        }

        if(dto.getStoreId() != null && dto.getStoreId() == (-1)){
            dto.setStoreId(null);
        }

        if(dto.getStart() == null || dto.getEnd() == null) {
            //Dateless Pattern
            if(dto.getStoreId() == null && dto.getDepartmentId() == null){
                domains = dao.findAll();
            }else if(dto.getStoreId() != null && dto.getDepartmentId() == null){
                store = storeService.getStore(dto.getStoreId());
                domains = dao.findByStore(store);
            }else if(dto.getStoreId() == null && dto.getDepartmentId() != null){
                department = departmentService.getDepartment(dto.getDepartmentId());
                domains = dao.findByDepartment(department);
            }else if(dto.getStoreId() != null && dto.getDepartmentId() != null){
                store = storeService.getStore(dto.getStoreId());
                department = departmentService.getDepartment(dto.getDepartmentId());
                domains = dao.findByStoreAndDepartment(store, department);
            }
        }else {
            //Date Pattern
            if (dto.getStoreId() == null && dto.getDepartmentId() == null) {
                domains = dao.findByDateGreaterThanEqualAndDateLessThanEqual(dto.getStart(), dto.getEnd());
            } else if (dto.getStoreId() != null && dto.getDepartmentId() == null) {
                store = storeService.getStore(dto.getStoreId());
                domains = dao.findByStoreAndDateGreaterThanEqualAndDateLessThanEqual(store, dto.getStart(), dto.getEnd());
            } else if (dto.getStoreId() == null && dto.getDepartmentId() != null) {
                department = departmentService.getDepartment(dto.getDepartmentId());
                domains = dao.findByDepartmentAndDateGreaterThanEqualAndDateLessThanEqual(department, dto.getStart(), dto.getEnd());
            } else if (dto.getStoreId() != null && dto.getDepartmentId() != null) {
                store = storeService.getStore(dto.getStoreId());
                department = departmentService.getDepartment(dto.getDepartmentId());
                domains = dao.findByStoreAndDepartmentAndDateGreaterThanEqualAndDateLessThanEqual(store, department, dto.getStart(), dto.getEnd());
            }
        }


        List<DepartmentRevenueExtendedAnalysesDTO> dtos = new ArrayList<>();
        if(domains != null){
            for (DepartmentRevenueDomain domain: domains) {
                DepartmentRevenueExtendedAnalysesDTO resultDTO = modelMapper.map(domain, DepartmentRevenueExtendedAnalysesDTO.class);
                try {
                    SizeDTO size = sizeService.getSizeForStoreDepartmentDate(domain.getStore().getId(), domain.getDepartment().getId(), domain.getDate());
                    resultDTO.setSize(size.getSize());
                    resultDTO.setRevenuePerSize(resultDTO.getAmount() / resultDTO.getSize());
                }catch (IllegalStateException e){
                    LOGGER.warn("Could not find a size for entry");
                }
                dtos.add(resultDTO);
            }
        }
        return dtos;
    }

    public DepartmentRevenueExtendedAnalysesDTO createDepartmentRevenueEntry(DepartmentRevenueDTO dto){
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
        return calculateDepartmentRevenueDTO(modelMapper.map(domain, DepartmentRevenueExtendedAnalysesDTO.class));
    }

    public DepartmentRevenueDTO getExistingDepartmentRevenue(DepartmentRevenueDTO dto){
        StoreDomain store = storeService.getStore(dto.getStoreId());
        DepartmentDomain department = departmentService.getDepartment(dto.getDepartmentId());

        //Validate and modify date
        Date date = dto.getDate();
        date = normalizeDate(date);

        DepartmentRevenueDomain domain = dao.findByStoreAndDepartmentAndDate(store, department, date);
        if(domain != null){
            return calculateDepartmentRevenueDTO(modelMapper.map(domain, DepartmentRevenueExtendedAnalysesDTO.class));
        }
        return null;
    }

    private DepartmentRevenueExtendedAnalysesDTO calculateDepartmentRevenueDTO(DepartmentRevenueExtendedAnalysesDTO basic){
        try {
            SizeDTO size = sizeService.getSizeForStoreDepartmentDate(basic.getStoreId(), basic.getDepartmentId(), basic.getDate());
            basic.setSizeDTO(size);
            basic.setSize(size.getSize());
            basic.setRevenuePerSize(basic.getAmount()/basic.getSize());

        }catch (IllegalStateException e){
            LOGGER.warn("Could not get size for department, date, location when calculating Extended Analyses");
        }
        return basic;
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
