package com.agoldberg.hercules.dailyentry;


import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.goal.GoalService;
import com.agoldberg.hercules.store.StoreDTO;
import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import com.agoldberg.hercules.tax.TaxDTO;
import com.agoldberg.hercules.tax.TaxService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DailyEntryService {
    @Autowired
    private DailyEntryDAO dao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private TaxService taxService;

    @Autowired
    private GoalService goalService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyEntryService.class);

    public List<DailyEntryExtendedAnalysisDTO> searchDailyEntry(SearchDTO dto){
        List<DailyEntryDomain> domains;
        StoreDomain store = null;
        if(dto.getStoreId() != null && dto.getStoreId() == (-1)){
            dto.setStoreId(null);
        }
        if(dto.getLod() == null){
            dto.setLod(LOD.ENTRY);
        }
        if(dto.getStart() == null || dto.getEnd() == null){
            if(dto.getStoreId() != null){
                store = storeService.getStore(dto.getStoreId());
                domains = dao.findByStoreOrderByDate(store);
            }else{
                domains = dao.findByOrderByDate();
            }
        }else{
            if(dto.getStoreId() != null){
                store = storeService.getStore(dto.getStoreId());
                domains = dao.findByStoreAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(store,dto.getStart(), dto.getEnd());
            }else{
                domains = dao.findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(dto.getStart(), dto.getEnd());
            }
        }

        List<DailyEntryExtendedAnalysisDTO> dtos = fastGoalAndTaxAssignment(domains);
        LOGGER.info("Returning Search List of Size: {}", dtos.size());

        if(dto.getLod() != LOD.ENTRY){
            LOGGER.info("Grouping entries by LOD: {}", dto.getLod());
            dtos = groupDTOS(dtos, dto.getLod());
            LOGGER.info("Grouped Entries, finals size: {}" , dtos.size());
        }

        return dtos;
    }

    public DailyEntryExtendedAnalysisDTO createDailyEntry(DailyEntryDTO dto){
        StoreDomain store = storeService.getStore(dto.getStoreId());
        Date date = dto.getDate();
        if(date == null){
            throw new IllegalArgumentException("Invalid Date");
        }
        DailyEntryDomain domain;
        if((domain = dao.findByStoreAndDate(store, date)) != null){
            LOGGER.warn("Overwrite Warning: Replacing Entry: {}, Store: {}, Date: {}", domain.getId(), domain.getStore().getName(), domain.getDate().toString());
            domain.setTransactionCount(dto.getTransactionCount());
            //tape
            domain.setCardTape(dto.getCardTape());
            domain.setCashTape(dto.getCashTape());
            domain.setCheckTape(dto.getCheckTape());
            domain.setTaxTape(dto.getTaxTape());
            //count
            domain.setCashCount(dto.getCashCount());
            domain.setCheckCount(dto.getCheckCount());
            domain.setCardUnit(dto.getCardUnit());

            domain.setPayoutReceipt(dto.getPayoutReceipt());
            domain.setMemo(dto.getMemo());
        }else{
            domain = new DailyEntryDomain();
            domain.setStore(store);
            domain.setDate(date);

            domain.setTransactionCount(dto.getTransactionCount());
            //tape
            domain.setCardTape(dto.getCardTape());
            domain.setCashTape(dto.getCashTape());
            domain.setCheckTape(dto.getCheckTape());
            domain.setTaxTape(dto.getTaxTape());
            //count
            domain.setCashCount(dto.getCashCount());
            domain.setCheckCount(dto.getCheckCount());
            domain.setCardUnit(dto.getCardUnit());

            domain.setPayoutReceipt(dto.getPayoutReceipt());
            domain.setMemo(dto.getMemo());

        }

        LOGGER.info("Saving Daily Entry: {}, Store: {}, Date: {}", domain.getId(), domain.getStore().getName(), domain.getDate().toString());

        domain = dao.save(domain);

        return calculateExtendedAnalysis(modelMapper.map(domain, DailyEntryExtendedAnalysisDTO.class));
    }

    public DailyEntryExtendedAnalysisDTO getExistingDailyEntry(DailyEntryDTO dto){
        StoreDomain store = storeService.getStore(dto.getStoreId());
        Date date = dto.getDate();
        if(date == null){
            throw new IllegalArgumentException("Invalid Date");
        }
        DailyEntryDomain domain;
        if((domain = dao.findByStoreAndDate(store, date)) != null){
            return calculateExtendedAnalysis(modelMapper.map(domain, DailyEntryExtendedAnalysisDTO.class));
        }else{
            return null;
        }
    }


    private DailyEntryExtendedAnalysisDTO calculateExtendedAnalysis(DailyEntryExtendedAnalysisDTO extended){

        extended.setActual(extended.getCardUnit() + extended.getCashCount() + extended.getCheckCount());
        extended.setRecorded(extended.getCardTape() + extended.getCashTape() + extended.getCheckTape());
        extended.setOverUnder(extended.getActual() - extended.getRecorded());


        extended.setValuePerTranscation(nanToZero(extended.getActual()/extended.getTransactionCount()));
        extended.setPercentageCash(nanToZero(extended.getCashCount()/extended.getActual()));
        extended.setPercentageCard(nanToZero(extended.getCardUnit()/extended.getActual()));
        extended.setPercentageCheck(nanToZero(extended.getCheckCount()/extended.getActual()));

        if(extended.getTaxDTO() != null) {
            extended.setCalculatedTax(extended.getActual() * extended.getTaxDTO().getRate());
        }
        return extended;
    }

    private double nanToZero(double number){
        if(Double.isNaN(number)){
            number = 0;
        }
        return number;
    }

    private List<DailyEntryExtendedAnalysisDTO> fastGoalAndTaxAssignment(List<DailyEntryDomain> domains){
        LOGGER.debug("Running Fast Goal and Taz Assignment: ");
        List<StoreDTO> stores = storeService.getEnabledStores();
        Map<Long,Stack<TaxDTO>> taxStoreMap = new HashMap<>();
        Map<Long,Stack<GoalDTO>> goalStoreMap = new HashMap<>();

        LOGGER.debug("Got store list: {} ", stores.size());
        for(StoreDTO store : stores){


            //Taxes
            List<TaxDTO> taxList = taxService.getTaxes(store.getId());
            if(!taxList.isEmpty()) {
                Collections.reverse(taxList);
                LOGGER.debug("For store: {}, got tax list of size: {}", store.getId(), taxList.size());
                Stack<TaxDTO> taxStack = new Stack<>();
                taxStack.addAll(taxList);
                LOGGER.debug("Converted Tax List to Stack.");
                LOGGER.debug("Entry at top of stack starts: {} and ends: {}", taxStack.peek().getStart(), taxStack.peek().getStart());
                taxStoreMap.put(store.getId(), taxStack);
            }
            //Goals
            List<GoalDTO> goalList = goalService.getGoales(store.getId());

            if(!goalList.isEmpty()) {
                Collections.reverse(goalList);
                LOGGER.debug("For store: {}, got goal list of size: {}", store.getId(), goalList.size());
                Stack<GoalDTO> goalStack = new Stack<>();
                goalStack.addAll(goalList);
                LOGGER.debug("Converted Goal List to Stack.");
                LOGGER.debug("Entry at top of stack starts: {} and ends: {}", goalStack.peek().getStart(), goalStack.peek().getStart());
                goalStoreMap.put(store.getId(), goalStack);
            }
        }


        List<DailyEntryExtendedAnalysisDTO> dtos = new ArrayList<>();
        LOGGER.debug("Performing Tax and Goal Assignment on List of Domains");
        for(int i = 0; i < domains.size(); i++){
            DailyEntryExtendedAnalysisDTO extendedDTO = modelMapper.map(domains.get(i),DailyEntryExtendedAnalysisDTO.class);
            LOGGER.debug("For entry: {}, got store ID: {}", i, extendedDTO.getStoreId());

            Stack<TaxDTO> taxStack = taxStoreMap.get(extendedDTO.getStoreId());
            Stack<GoalDTO> goalStack = goalStoreMap.get(extendedDTO.getStoreId());

            if(taxStack != null && !taxStack.empty()){
                LOGGER.debug("Got stack of size: {}", taxStack.size());

                LOGGER.debug("Entry at top of stack starts: {} and ends: {}", taxStack.peek().getStart(), taxStack.peek().getEnd());
                LOGGER.debug("Current entry date is: {}", extendedDTO.getDate().toString());
                while(!taxStack.empty() && extendedDTO.getDate().before(taxStack.peek().getStart()) || extendedDTO.getDate().after(taxStack.peek().getEnd())){
                    LOGGER.debug("Current entry with date: {}, is before the start or after the end of the entry at the top of the stack: {}", extendedDTO.getDate(), taxStack.peek().getStart());
                    taxStack.pop();
                    LOGGER.debug("Popping Stack");
                    if(taxStack.empty()){
                        break;
                    }
                }

                if(!taxStack.empty() && taxStack.peek().getStart().before(extendedDTO.getDate()) && taxStack.peek().getEnd().after(extendedDTO.getDate())) {
                    LOGGER.debug("Preparing to assign top of stack to dto");
                    LOGGER.debug("Entry at top of stack starts: {} and ends: {}", taxStack.peek().getStart(), taxStack.peek().getEnd());
                    extendedDTO.setTaxDTO(taxStack.peek());

                } else if (!taxStack.empty()){

                    LOGGER.debug("Could not assign Tax to Entry:");
                    LOGGER.debug("Entry at top of stack starts: {} and ends: {}", taxStack.peek().getStart(), taxStack.peek().getStart());
                    LOGGER.debug("Current entry date is: {}", extendedDTO.getDate().toString());
                }else{
                    LOGGER.debug("Stack Empty");
                }

            }else{
                LOGGER.debug("Tax Stack is Empty");
            }

            if(goalStack != null && !goalStack.empty()){
                LOGGER.debug("Got stack of size: {}", goalStack.size());

                LOGGER.debug("Entry at top of stack starts: {} and ends: {}", goalStack.peek().getStart(), goalStack.peek().getEnd());
                LOGGER.debug("Current entry date is: {}", extendedDTO.getDate().toString());
                while(!goalStack.empty() && extendedDTO.getDate().before(goalStack.peek().getStart()) || extendedDTO.getDate().after(goalStack.peek().getEnd())){
                    LOGGER.debug("Current entry with date: {}, is before the start of the entry at the top of the stack: {}", extendedDTO.getDate(), goalStack.peek().getStart());
                    goalStack.pop();
                    LOGGER.debug("Popping Stack");
                    if(goalStack.empty()){
                        break;
                    }
                }

                if(!goalStack.empty() && goalStack.peek().getStart().before(extendedDTO.getDate()) && goalStack.peek().getEnd().after(extendedDTO.getDate())) {
                    LOGGER.debug("Preparing to assign top of stack to dto");
                    LOGGER.debug("Entry at top of stack starts: {} and ends: {}", goalStack.peek().getStart(), goalStack.peek().getEnd());
                    extendedDTO.setGoalDTO(goalStack.peek());

                } else if (!goalStack.empty()){

                    LOGGER.debug("Could not assign Goal to Entry:");
                    LOGGER.debug("Entry at top of stack starts: {} and ends: {}", goalStack.peek().getStart(), goalStack.peek().getStart());
                    LOGGER.debug("Current entry date is: {}", extendedDTO.getDate().toString());
                }else{
                    LOGGER.debug("Stack Empty");
                }

            }else{
                LOGGER.debug("Goal Stack is Empty");
            }

            dtos.add(calculateExtendedAnalysis(extendedDTO));

        }
        return dtos;
    }

    private List<DailyEntryExtendedAnalysisDTO> groupDTOS(List<DailyEntryExtendedAnalysisDTO> dtos, LOD lod){
        LOGGER.debug("Grouping entries by LOD: {}", lod);
        Stack<DailyEntryExtendedAnalysisDTO> grouping = new Stack<>();
        int count = 0;
        for(DailyEntryExtendedAnalysisDTO dto : dtos){
            if(grouping.empty()){
                grouping.push(smoothDate(dto, lod));
            }else{
                switch (lod){
                    case ENTRY:
                        grouping.push(dto);
                        break;
                    case DAY:
                        if(grouping.peek().getDate().getDate() == dto.getDate().getDate()){
                            DailyEntryExtendedAnalysisDTO current = grouping.pop();
                            count++;
                            DailyEntryExtendedAnalysisDTO newGrouping = groupEntries(current, dto, count);
                            grouping.push(smoothDate(newGrouping, lod));
                        }else{
                            LOGGER.debug("Dates are {} and {} which do not match for this LOD. Pushing to stack as new entry", grouping.peek().getDate(), dto.getDate());
                            count = 1;
                            grouping.push(smoothDate(dto, lod));
                        }
                        break;
                    case MONTH:
                        if(grouping.peek().getDate().getMonth() == dto.getDate().getMonth()){
                            DailyEntryExtendedAnalysisDTO current = grouping.pop();
                            count++;
                            DailyEntryExtendedAnalysisDTO newGrouping = groupEntries(current, dto, count);
                            grouping.push(smoothDate(newGrouping, lod));
                        }else{
                            LOGGER.debug("Dates are {} and {} which do not match for this LOD. Pushing to stack as new entry", grouping.peek().getDate(), dto.getDate());
                            count = 1;
                            grouping.push(smoothDate(dto, lod));
                        }
                        break;
                    case YEAR:
                        if(grouping.peek().getDate().getYear() == dto.getDate().getYear()){
                            DailyEntryExtendedAnalysisDTO current = grouping.pop();
                            count++;
                            DailyEntryExtendedAnalysisDTO newGrouping = groupEntries(current, dto, count);
                            grouping.push(smoothDate(newGrouping, lod));
                        }else{
                            LOGGER.debug("Dates are {} and {} which do not match for this LOD. Pushing to stack as new entry", grouping.peek().getDate(), dto.getDate());
                            count = 1;
                            grouping.push(smoothDate(dto, lod));
                        }
                        break;
                }

            }
        }

        return new ArrayList<DailyEntryExtendedAnalysisDTO>(grouping);
    }

    private DailyEntryExtendedAnalysisDTO groupEntries(DailyEntryExtendedAnalysisDTO existing, DailyEntryExtendedAnalysisDTO adding, int size){
        //Basic DTO

        existing.setTransactionCount(existing.getTransactionCount()+ adding.getTransactionCount());
        existing.setCashCount(existing.getCashCount()+adding.getCashCount());
        existing.setCheckCount(existing.getCheckCount()+existing.getCheckCount());
        existing.setCardUnit(existing.getCardUnit() + adding.getCardUnit());
        existing.setPayoutReceipt(existing.getPayoutReceipt()+adding.getPayoutReceipt());
        existing.setCashTape(existing.getCashTape()+adding.getCashTape());
        existing.setCardTape(existing.getCardTape()+adding.getCardTape());
        existing.setTaxTape(existing.getTaxTape()+adding.getTaxTape());

        //Extended DTO
        existing.setActual(existing.getActual() + adding.getActual());
        existing.setRecorded(existing.getRecorded() + adding.getRecorded());
        existing.setOverUnder(existing.getOverUnder() + adding.getOverUnder());
        existing.setCalculatedTax(existing.getCalculatedTax()+adding.getCalculatedTax());

        //Exiting AVGs
        existing.setValuePerTranscation(avg(existing.getValuePerTranscation(), adding.getValuePerTranscation(),size));
        existing.setPercentageCard(avg(existing.getPercentageCard(), adding.getPercentageCard(),size));
        existing.setPercentageCash(avg(existing.getPercentageCash(),adding.getPercentageCash(),size));
        existing.setPercentageCheck(avg(existing.getPercentageCheck(),adding.getPercentageCheck(),size));

        return existing;
    }

    private double avg(double avg, double add, int count){
        return avg + ((add-avg)/count);
    }
    private DailyEntryExtendedAnalysisDTO smoothDate(DailyEntryExtendedAnalysisDTO entry, LOD lod){
        switch (lod){
            case MONTH:
                entry.getDate().setDate(1);
                break;
            case YEAR:
                entry.getDate().setDate(1);
                entry.getDate().setMonth(1);
                break;
                default:
                    break;
        }
        return entry;
    }

    public DailyEntryDTO getDailyEntry(Long id){
        LOGGER.info("Getting single Daily Entry by ID: {}", id);
        return modelMapper.map(dao.getOne(id), DailyEntryDTO.class);
    }

}
