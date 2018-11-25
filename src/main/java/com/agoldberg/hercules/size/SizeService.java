package com.agoldberg.hercules.size;

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
public class SizeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SizeService.class);

    @Autowired
    private SizeDAO dao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

    public void createSize(SizeDTO dto){
        if(dto.getStoreId() == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(dto.getStoreId());
        DepartmentDomain department = departmentService.getDepartment(dto.getDepartmentId());

        if(dto.getSize() < 0){
            throw new IllegalArgumentException("Bad Rate");
        }

        if(dto.getStart() == null || dto.getEnd() == null){
            throw new IllegalArgumentException("Null Dates");
        }

        if(dto.getStart().after(dto.getEnd())){
            throw new IllegalArgumentException("Bad Date Range");
        }

        if(dao.findByStoreAndDepartmentAndStartLessThanEqualAndEndGreaterThanEqual(store, department, dto.getEnd(), dto.getStart()) != null){
            throw new IllegalStateException("Date Overlaps with Existing Entry");
        }


        SizeDomain domain = new SizeDomain(store, department, dto.getSize(), dto.getStart(), dto.getEnd());
        domain = dao.save(domain);
        LOGGER.info("Created new tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getSize(),domain.getStart(),domain.getEnd() );

    }

    public void modifySize(SizeDTO dto){
        if(dto.getId() == null){
            throw new IllegalArgumentException("Bad ID");
        }

        if(dto.getStoreId() == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(dto.getStoreId());

        DepartmentDomain department = departmentService.getDepartment(dto.getDepartmentId());

        if(dto.getSize() < 0){
            throw new IllegalArgumentException("Bad Rate");
        }

        if(dto.getStart() == null || dto.getEnd() == null){
            throw new IllegalArgumentException("Null Dates");
        }

        if(dto.getStart().after(dto.getEnd())){
            throw new IllegalArgumentException("Bad Date Range");
        }

        SizeDomain existing = dao.findByIdNotAndStoreAndDepartmentAndStartLessThanEqualAndEndGreaterThanEqual(dto.getId(),store, department, dto.getEnd(), dto.getStart());

        if(existing != null && !existing.getId().equals(dto.getId())){
            throw new IllegalStateException("Overlapping Date Range");
        }

        SizeDomain domain = dao.getOne(dto.getId());

        domain.setStore(store);
        domain.setStart(dto.getStart());
        domain.setEnd(dto.getEnd());
        domain.setSize(dto.getSize());

        domain = dao.save(domain);
        LOGGER.info("Modified tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getSize(),domain.getStart(),domain.getEnd() );

    }



    public List<SizeDTO> getSizesForStore(Long id){
        if(id == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(id);
        List<SizeDomain> domains = dao.findByStore(store);
        List<SizeDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, SizeDTO.class)));
        LOGGER.info("Got list of Taxes for store: {}, size: {}", store.getName(), dtos.size());
        return dtos;
    }

    public List<SizeDTO> getSizesForDepartment(Long id){
        if(id == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        DepartmentDomain department = departmentService.getDepartment(id);
        List<SizeDomain> domains = dao.findByDepartment(department);
        List<SizeDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, SizeDTO.class)));
        LOGGER.info("Got list of Taxes for department: {}, size: {}", department.getName(), dtos.size());
        return dtos;
    }

    public List<SizeDTO> getSizes(){
        List<SizeDomain> domains = dao.findAll();
        List<SizeDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, SizeDTO.class)));
        LOGGER.info("Got list of Sizes, size: {}", dtos.size());
        return dtos;
    }

    public SizeDTO getSizeForStoreDepartmentDate(Long storeId, Long departmentId, Date date) {
        StoreDomain store = storeService.getStore(storeId);
        DepartmentDomain department = departmentService.getDepartment(departmentId);
        if (date == null) {
            throw new IllegalArgumentException("Bad Date");
        }
        SizeDomain domain = dao.findByStoreAndDepartmentAndStartLessThanEqualAndEndGreaterThanEqual(store, department, date, date);
        if (domain == null) {
            throw new IllegalStateException("Could not find size for specified store, department, and date");
        } else {
            LOGGER.info("Got Size: {}, for Store: {}, Department: {}, Date: {}", store.getName(), department.getName(), date.toString());
            return modelMapper.map(domain, SizeDTO.class);
        }
    }


    public SizeDTO getSize(Long id){
        return modelMapper.map(dao.getOne(id), SizeDTO.class);
    }

    public void deleteSize(SizeDTO dto){
        dao.delete(modelMapper.map(dto, SizeDomain.class));
    }

}
