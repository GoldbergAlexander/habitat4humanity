package com.agoldberg.hercules.service;

import com.agoldberg.hercules.builder.ProcessedRevenueDomainBuilder;
import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.EnteredSearchDTO;
import com.agoldberg.hercules.dto.ProcessedRevenueDTO;
import com.agoldberg.hercules.dto.ProcessedRevenueDataAndSummaryDTO;
import com.agoldberg.hercules.event.RevenueEnteredEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessedRevenueServiceImpl implements ApplicationListener<RevenueEnteredEvent>, ProcessedRevenueService {

    @Autowired
    private ProcessedRevenueDAO processedRevenueDAO;

    @Autowired
    private StoreLocationServiceImpl storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${taxrate:.07}")
    private double taxRate;


    @Override
    public ProcessedRevenueDataAndSummaryDTO getProcessedRevenues(EnteredSearchDTO dto){
        List<ProcessedRevenueDomain> domains;
        if(dto.getLocationId() != null){
            StoreLocationDomain locationDomain = storeLocationService.getStoreLocation(dto.getLocationId());
            domains = processedRevenueDAO.findByLocationDomainAndDateBetween(locationDomain, dto.getStartingDate(), dto.getEndingDate());
        }else if(dto.getStartingDate() != null && dto.getEndingDate() != null){
            domains = processedRevenueDAO.findByDateBetween(dto.getStartingDate(), dto.getEndingDate());
        }else{
            domains = processedRevenueDAO.findAll();
        }

        List<ProcessedRevenueDTO> dtos = new ArrayList<>();
        ProcessedRevenueDTO summary = new ProcessedRevenueDTO();
        for(ProcessedRevenueDomain domain : domains){
            dtos.add(modelMapper.map(domain, ProcessedRevenueDTO.class));

            /** Create Summary Row **/
            summary.setActualIntake(summary.getActualIntake() + domain.getActualIntake());
            summary.setActualTaxableIntake(summary.getActualTaxableIntake() + domain.getActualTaxableIntake());
            summary.setActualTaxIntake(summary.getActualTaxIntake() + domain.getActualTaxIntake());
            summary.setTapeIntake(summary.getTapeIntake() + domain.getTapeIntake());
            summary.setTapePreTaxIntake(summary.getTapePreTaxIntake() + domain.getTapePreTaxIntake());
            summary.setTapeTaxableIntake(summary.getTapeTaxableIntake() + domain.getTapeTaxableIntake());
            summary.setOverUnder(summary.getOverUnder() + domain.getOverUnder());
            summary.setTaxCount(summary.getTaxCount() + domain.getTaxCount());
        }
        return new ProcessedRevenueDataAndSummaryDTO(dtos, summary);
    }

    @Override
    public void onApplicationEvent(RevenueEnteredEvent revenueEnteredEvent) {
        processRevenue(revenueEnteredEvent.getEnteredRevenue());
    }

    private void processRevenue(EnteredRevenueDomain er){
        /**
         * Check if the er already exists for the date and location
         * If it does update our processing entry to relate to the new entry and not the old
         */

        double actualIntake = er.getCashCount() +
                er.getCheckCount() +
                er.getCardUnit() +
                er.getPayoutReceipt();

//        Actual Total Daily Intake
        double actualPreTaxIntake = er.getCashCount() +
                er.getCheckCount() +
                er.getCheckCount() +
                er.getCardUnit() +
                er.getPayoutReceipt() -
                er.getTaxTape();

//        Actual Daily Pre-Tax Intake
        double actualTaxableIntake = er.getCashCount() +
                er.getCheckCount() +
                er.getCardUnit() +
                er.getPayoutReceipt() -
                er.getTaxTape() -
                er.getVehicleSale();

//        Actual Daily Taxable Intake
        double actualTaxIntake = er.getCashTape() +
                er.getCheckTape() +
                er.getCardTape();
//        Total Daily Tape
        double tapeIntake = er.getCashTape() +
                er.getCheckTape() +
                er.getCardTape();

//        Total Daily Tape
        double tapePreTaxIntake = er.getCashTape() +
                er.getCheckTape() +
                er.getCardTape() -
                er.getTaxTape() -
                er.getVehicleSale();

//        Daily Taxable Tape
        double tapeTaxableIntake = er.getCashTape() +
                er.getCheckTape() +
                er.getCardTape() +
                er.getTaxTape() +
                er.getVehicleSale();

//        Over Under
        double overUnder = actualIntake - tapeIntake;

//        Tax Count
        double taxCount = (actualIntake - overUnder - er.getVehicleSale()) -
                ((actualIntake - overUnder - er.getVehicleSale()) /
                        (1+taxRate));

        ProcessedRevenueDomain processedRevenue = new ProcessedRevenueDomainBuilder()
                .setActualIntake(actualIntake)
                .setActualPreTaxIntake(actualPreTaxIntake)
                .setActualTaxableIntake(actualTaxableIntake)
                .setActualTaxIntake(actualTaxIntake)
                .setActualPreTaxIntake(actualPreTaxIntake)
                .setTapeIntake(tapeIntake)
                .setTapePreTaxIntake(tapePreTaxIntake)
                .setTapeTaxableIntake(tapeTaxableIntake)
                .setOverUnder(overUnder)
                .setTaxCount(taxCount)
                .setEnteredRevenue(er)
                .createProcessedRevenueDomain();



        /**
         * See if we already have a processed Revenue for this date and location,
         * If so, update it with the new data by setting our new object's Id to the existing ID;
         */
        ProcessedRevenueDomain existing = processedRevenueDAO.findByLocationDomainAndDate(er.getLocation(), er.getDate());
        if(existing != null){
            processedRevenue.setId(existing.getId());
        }
        processedRevenueDAO.save(processedRevenue);
    }


}
