package com.agoldberg.hercules.summary;

import com.agoldberg.hercules.dailyentry.DailyEntryExtendedAnalysisDTO;
import com.agoldberg.hercules.dailyentry.DailyEntryService;
import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.goal.GoalService;
import com.agoldberg.hercules.store.StoreDTO;
import com.agoldberg.hercules.store.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SummaryService {
    @Autowired
    private DailyEntryService dailyEntryService;
    
    @Autowired
    private StoreService storeService;
    
    @Autowired
    private GoalService goalService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryService.class);


    public List<SummaryDTO> getIndividualSummary(SearchDTO dto){
        if(dto.getStoreIds() == null || dto.getStoreIds().size() < 1){
            throw new IllegalArgumentException("Must Include At Least One Store");
        }
        if(dto.getDate() == null){
            throw new IllegalArgumentException("Bad Date");
        }
        
        List<SummaryDTO> summaryDTOS = new ArrayList<>();
        
        Date currentYearDate = dto.getDate();
        Date currentYearFirstOfMonth = new Date(dto.getDate().getYear(), dto.getDate().getMonth(), 1);
        
        Date priorYearDate = new Date(currentYearDate.getYear()-1, currentYearDate.getMonth(), currentYearDate.getDate());
        Date priorYearFirstOfMonth = new Date(currentYearFirstOfMonth.getYear()-1, currentYearFirstOfMonth.getMonth(), 1);
        
        for(Long id : dto.getStoreIds()){
            
            //Get Objects
            SummaryDTO summaryDTO = new SummaryDTO();
            StoreDTO storeDTO = storeService.getStoreDTO(id);
            summaryDTO.setStoreDTO(storeDTO);
            
            try {
                GoalDTO goalDTO = goalService.getGoalDTOforStoreAndDate(storeDTO.getId(), currentYearDate);
                summaryDTO.setGoalDTO(goalDTO);
            }catch (IllegalStateException e){
                LOGGER.warn("Could not get Goal for store and date");
            }
            
            //Get Data
            double actual = 0;
            List<DailyEntryExtendedAnalysisDTO> list = 
                    dailyEntryService.searchDailyEntry(
                            new com.agoldberg.hercules.dailyentry.SearchDTO(
                                    "", storeDTO.getId(), currentYearFirstOfMonth, currentYearDate));
            
            for(DailyEntryExtendedAnalysisDTO entry : list){
                actual += entry.getActual();
            }
            summaryDTO.setCurrentYearActual(actual);

            dailyEntryService.searchDailyEntry(
                    new com.agoldberg.hercules.dailyentry.SearchDTO(
                            "", storeDTO.getId(), priorYearFirstOfMonth, priorYearDate));

            for(DailyEntryExtendedAnalysisDTO entry : list){
                actual += entry.getActual();
            }
            
            
            summaryDTO.setPriorYearActual(actual);
            
            //Calculate
            summaryDTO.setMonthOverMonthActual(summaryDTO.getCurrentYearActual()-summaryDTO.getPriorYearActual());
            summaryDTO.setMonthOverMonthPercent(summaryDTO.getCurrentYearActual()/summaryDTO.getPriorYearActual());
            
            if(summaryDTO.getGoalDTO() != null){
                summaryDTO.setCurrentYearGoal(summaryDTO.getPriorYearActual()*(1+summaryDTO.getGoalDTO().getRate()));
                summaryDTO.setMonthOverGoalActual(summaryDTO.getCurrentYearActual() - summaryDTO.getCurrentYearGoal());
                summaryDTO.setMonthOverGoalPercent(summaryDTO.getCurrentYearActual()/summaryDTO.getCurrentYearGoal());
            }
            
            summaryDTOS.add(summaryDTO);
        }
        return summaryDTOS;
    }
    
    public SummaryDTO getSummary(SearchDTO dto){
        List<SummaryDTO> list = getIndividualSummary(dto);
        
        SummaryDTO returnObject = new SummaryDTO();
        for(SummaryDTO summaryDTO : list){
            returnObject.setCurrentYearActual(returnObject.getCurrentYearActual() + summaryDTO.getCurrentYearActual());
            returnObject.setPriorYearActual(returnObject.getPriorYearActual() + summaryDTO.getPriorYearActual());
            returnObject.setCurrentYearGoal(returnObject.getCurrentYearGoal() + summaryDTO.getCurrentYearGoal());
        }

        returnObject.setMonthOverGoalActual(returnObject.getCurrentYearActual() - returnObject.getCurrentYearGoal());
        returnObject.setMonthOverGoalPercent(returnObject.getCurrentYearActual()/returnObject.getCurrentYearGoal());
        
        return returnObject;
    }

}
