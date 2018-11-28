package com.agoldberg.hercules.dailyentry;


import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.goal.GoalService;
import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.store.StoreService;
import com.agoldberg.hercules.tax.TaxDTO;
import com.agoldberg.hercules.tax.TaxService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public DailyEntryExtendedAnalysisDOT createDailyEntry(DailyEntryDTO dto){
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

        return calculateExtendedAnalysis(modelMapper.map(domain, DailyEntryExtendedAnalysisDOT.class));
    }

    public DailyEntryExtendedAnalysisDOT getExistingDailyEntry(DailyEntryDTO dto){
        StoreDomain store = storeService.getStore(dto.getStoreId());
        Date date = dto.getDate();
        if(date == null){
            throw new IllegalArgumentException("Invalid Date");
        }
        DailyEntryDomain domain;
        if((domain = dao.findByStoreAndDate(store, date)) != null){
            return calculateExtendedAnalysis(modelMapper.map(domain, DailyEntryExtendedAnalysisDOT.class));
        }else{
            return null;
        }
    }


    private DailyEntryExtendedAnalysisDOT calculateExtendedAnalysis(DailyEntryExtendedAnalysisDOT extended){
        try {
            TaxDTO tax = taxService.getTaxDTOForLocationAndDate(extended.getStoreId(), extended.getDate());
            extended.setTaxDTO(tax);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Could not find a Tax Domain for the Store");
        }
        try {
            GoalDTO goal = goalService.getGoalDTOforStoreAndDate(extended.getStoreId(), extended.getDate());
            extended.setGoalDTO(goal);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Could not find a Goal Domain for the Store");
        }
        extended.setActual(extended.getCardUnit() + extended.getCashCount() + extended.getCheckCount());
        extended.setRecorded(extended.getCardTape() + extended.getCashTape() + extended.getCheckTape());
        extended.setOverUnder(extended.getActual() - extended.getRecorded());
        extended.setValuePerTranscation(extended.getActual()/extended.getTransactionCount());
        extended.setPercentageCash(extended.getCashCount()/extended.getActual());
        extended.setPercentageCard(extended.getCardUnit()/extended.getActual());
        extended.setPercentageCheck(extended.getCheckCount()/extended.getActual());

        if(extended.getTaxDTO() != null) {
            extended.setCalculatedTax(extended.getActual() * extended.getTaxDTO().getRate());
        }
        return extended;
    }


}
