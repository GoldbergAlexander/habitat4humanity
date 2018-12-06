package com.agoldberg.hercules.tax;

import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxService.class);

    @Autowired
    private TaxDAO dao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ModelMapper modelMapper;

    public TaxDTO getTaxDTOForLocationAndDate(Long storeId, Date date){
        //Not checking if store exists, just searching with object;
        StoreDomain store = storeService.getStore(storeId);
//        StoreDomain store = new StoreDomain();
//        store.setId(storeId);

        LOGGER.info("Finding tax domain for store: {}, and date: {} ", store.getName(), date.toString());
        TaxDomain domain = dao.findByStoreAndStartBeforeAndEndAfter(store,date,date);
        if(domain == null){
            throw new IllegalArgumentException("Could not find Tax for Store and Date");
        }
        return modelMapper.map(domain,TaxDTO.class);
    }

    public TaxDomain getTaxForLocationAndDate(StoreDomain store, Date date){
        LOGGER.info("Finding tax domain for store: {}, and date: {} ", store.getName(), date.toString());
        return dao.findByStoreAndStartBeforeAndEndAfter(store,date,date);
    }

    public void createTax(TaxDTO dto){
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

        if(dao.findByStoreAndStartLessThanEqualAndEndGreaterThanEqual(store, dto.getEnd(), dto.getStart()) != null){
            throw new IllegalStateException("Date Overlaps with Existing Entry");
        }


        TaxDomain domain = new TaxDomain(store, dto.getRate(), dto.getStart(), dto.getEnd());
        domain = dao.save(domain);
        LOGGER.info("Created new tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getRate(),domain.getStart(),domain.getEnd() );

    }

    public void modifyTax(TaxDTO dto){
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

        TaxDomain existing = dao.findByIdNotAndStoreAndStartLessThanEqualAndEndGreaterThanEqual(dto.getId(), store, dto.getEnd(), dto.getStart());

        if(existing != null && !existing.getId().equals(dto.getId())){
            throw new IllegalStateException("Overlapping Date Range");
        }

        TaxDomain domain = dao.getOne(dto.getId());

        domain.setStore(store);
        domain.setStart(dto.getStart());
        domain.setEnd(dto.getEnd());
        domain.setRate(dto.getRate());

        domain = dao.save(domain);
        LOGGER.info("Modified tax domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getRate(),domain.getStart(),domain.getEnd() );

    }



    public List<TaxDTO> getTaxes(Long id){
        if(id == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(id);
        List<TaxDomain> domains = dao.findByStoreOrderByStart(store);
        List<TaxDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, TaxDTO.class)));
        LOGGER.info("Got list of Taxes for store: {}, size: {}", store.getName(), dtos.size());
        return dtos;
    }

    public List<TaxDTO> getTaxes(){
        List<TaxDomain> domains = dao.findAll();
        List<TaxDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, TaxDTO.class)));
        LOGGER.info("Got list of Taxes, size: {}", dtos.size());
        return dtos;
    }

    public TaxDTO getTax(Long id){
        return modelMapper.map(dao.getOne(id), TaxDTO.class);
    }

    public void deleteTax(TaxDTO dto){
        dao.delete(modelMapper.map(dto, TaxDomain.class));
    }

}
