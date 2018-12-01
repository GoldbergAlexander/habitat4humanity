package com.agoldberg.hercules.goal;

import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoalService.class);

    @Autowired
    private GoalDAO dao;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ModelMapper modelMapper;

    public GoalDTO getGoalDTOforStoreAndDate(Long storeId, Date date){
        StoreDomain store = storeService.getStore(storeId);
        LOGGER.info("Finding Goal domain for store: {}, and date: {} ", store.getName(), date.toString());
        GoalDomain domain = dao.findByStoreAndStartBeforeAndEndAfter(store,date,date);
        if(domain == null){
            throw new IllegalArgumentException("Could not find Goal for Store and Date");
        }
        return modelMapper.map(domain,GoalDTO.class);
    }

    public void createGoal(GoalDTO dto){
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


        GoalDomain domain = new GoalDomain(store, dto.getRate(), dto.getStart(), dto.getEnd());
        domain = dao.save(domain);
        LOGGER.info("Created new goal domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getRate(),domain.getStart(),domain.getEnd() );

    }

    public void modifyGoal(GoalDTO dto){
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

        GoalDomain existing = dao.findByIdNotAndStoreAndStartLessThanEqualAndEndGreaterThanEqual(dto.getStoreId(),store, dto.getEnd(), dto.getStart());

        if(existing == null){
            existing = dao.findByStoreAndEndAfterOrStartBefore(store, dto.getEnd(), dto.getEnd());
        }

        if(existing != null && !existing.getId().equals(dto.getId())){
            throw new IllegalStateException("Bad Date Range");
        }

        GoalDomain domain = dao.getOne(dto.getId());

        domain.setStore(store);
        domain.setStart(dto.getStart());
        domain.setEnd(dto.getEnd());
        domain.setRate(dto.getRate());

        domain = dao.save(domain);
        LOGGER.info("Modified goal domain with ID: {}, for location: {}, at rate: {}, starting: {}, ending: {}",
                domain.getId(), domain.getStore().getName(), domain.getRate(),domain.getStart(),domain.getEnd() );

    }



    public List<GoalDTO> getGoales(Long id){
        if(id == null){
            throw new IllegalArgumentException("Bad Location ID");
        }
        StoreDomain store = storeService.getStore(id);
        List<GoalDomain> domains = dao.findByStore(store);
        List<GoalDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, GoalDTO.class)));
        LOGGER.info("Got list of Goales for store: {}, size: {}", store.getName(), dtos.size());
        return dtos;
    }

    public List<GoalDTO> getGoales(){
        List<GoalDomain> domains = dao.findAll();
        List<GoalDTO> dtos = new ArrayList<>();
        domains.forEach(domain -> dtos.add(modelMapper.map(domain, GoalDTO.class)));
        LOGGER.info("Got list of Goales, size: {}", dtos.size());
        return dtos;
    }

    public GoalDTO getGoal(Long id){
        return modelMapper.map(dao.getOne(id), GoalDTO.class);
    }

    public void deleteGoal(GoalDTO dto){
        dao.delete(modelMapper.map(dto, GoalDomain.class));
    }


}
