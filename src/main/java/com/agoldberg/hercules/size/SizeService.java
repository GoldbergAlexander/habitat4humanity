package com.agoldberg.hercules.size;

import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SizeService.class);

    @Autowired
    private SizeDAO dao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ModelMapper modelMapper;

    public void createTax(SizeDTO dto){
        if(dto.getStoreId() == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(dto.getStoreId());

        if(dto.getRate() < 0 || dto.getRate() > 1){
            throw new IllegalArgumentException("Bad Rate");
        }

        if(dto.getStart() == null || dto.getEnd() == null){
            throw new IllegalArgumentException("Null Dates");
        }

        if(dto.getStart().after(dto.getEnd())){
            throw new IllegalArgumentException("Bad Date Range");
        }

        if(dao.findByStoreAndEndAfterOrStartBefore(store, dto.getStart(), dto.getEnd()) != null){
            throw new IllegalStateException("Bad Date Range");
        }

        SizeDomain domain = new SizeDomain(store, dto.getRate(), dto.getStart(), dto.getEnd());
        domain = dao.save(domain);
        LOGGER.info("Created new tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getSize(),domain.getStart(),domain.getEnd() );

    }

    public void modifyTax(SizeDTO dto){
        if(dto.getId() == null){
            throw new IllegalArgumentException("Bad ID");
        }

        if(dto.getStoreId() == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(dto.getStoreId());

        if(dto.getRate() < 0 || dto.getRate() > 1){
            throw new IllegalArgumentException("Bad Rate");
        }

        if(dto.getStart() == null || dto.getEnd() == null){
            throw new IllegalArgumentException("Null Dates");
        }

        if(dto.getStart().after(dto.getEnd())){
            throw new IllegalArgumentException("Bad Date Range");
        }

        SizeDomain existing = dao.findByStoreAndEndAfterOrStartBefore(store, dto.getStart(), dto.getEnd());

        if(existing != null && !existing.getId().equals(dto.getId())){
            throw new IllegalStateException("Bad Date Range");
        }

        SizeDomain domain = dao.getOne(dto.getId());

        domain.setStore(store);
        domain.setStart(dto.getStart());
        domain.setEnd(dto.getEnd());
        domain.setSize(dto.getRate());

        domain = dao.save(domain);
        LOGGER.info("Modified tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getSize(),domain.getStart(),domain.getEnd() );

    }



    public List<SizeDTO> getTaxes(Long id){
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

    public List<SizeDTO> getTaxes(){
        List<SizeDomain> domains = dao.findAll();
        List<SizeDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, SizeDTO.class)));
        LOGGER.info("Got list of Taxes, size: {}", dtos.size());
        return dtos;
    }

    public SizeDTO getTax(Long id){
        return modelMapper.map(dao.getOne(id), SizeDTO.class);
    }

    public void deleteTax(SizeDTO dto){
        dao.delete(modelMapper.map(dto, SizeDomain.class));
    }

}
