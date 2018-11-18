package com.agoldberg.hercules.service;

import com.agoldberg.hercules.builder.ProcessedRevenueDomainBuilder;
import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.store.StoreDomain;
import com.agoldberg.hercules.dto.EnteredSearchDTO;
import com.agoldberg.hercules.dto.ProcessedRevenueDTO;
import com.agoldberg.hercules.dto.ProcessedRevenueDataAndSummaryDTO;
import com.agoldberg.hercules.event.RevenueEnteredEvent;
import com.agoldberg.hercules.store.StoreServiceImpl;
import org.apache.commons.math3.util.Precision;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProcessedRevenueServiceImpl implements ApplicationListener<RevenueEnteredEvent>, ProcessedRevenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessedRevenueService.class);

    @Autowired
    private ProcessedRevenueDAO processedRevenueDAO;

    @Autowired
    private StoreServiceImpl storeLocationService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${taxrate:.07}")
    private double taxRate;


    @Override
    public ProcessedRevenueDataAndSummaryDTO getProcessedRevenues(EnteredSearchDTO dto){
        List<ProcessedRevenueDomain> domains;
        if(dto.getLocationId() != null){
            StoreDomain locationDomain = storeLocationService.getStore(dto.getLocationId());
            domains = processedRevenueDAO.findByLocationDomainAndDateBetween(locationDomain, dto.getStartingDate(), dto.getEndingDate());
        }else if(dto.getStartingDate() != null && dto.getEndingDate() != null){
            domains = processedRevenueDAO.findByDateBetween(dto.getStartingDate(), dto.getEndingDate());
        }else{
            domains = processedRevenueDAO.findAll();
        }

        List<ProcessedRevenueDTO> dtos = new ArrayList<>();
        ProcessedRevenueDTO summary = new ProcessedRevenueDTO();
        Set<StoreDomain> locationDomainsSet = new HashSet<>();
        for(ProcessedRevenueDomain domain : domains){
            locationDomainsSet.add(domain.getLocationDomain());
            dtos.add(modelMapper.map(domain, ProcessedRevenueDTO.class));
            addToSummary(summary, domain);
        }

        //Get Summary of Summary (locations count for now)
        summary.setLocationsContainedInDTO(locationDomainsSet.size());



        LOGGER.info("Returning list of processed revenue for dates between: {} and {}, filtered by location: {}, size: {}",dto.getStartingDate(), dto.getEndingDate() , dto.getLocationName() ,dtos.size());
        return new ProcessedRevenueDataAndSummaryDTO(dtos, summary);
    }

    private void addToSummary(ProcessedRevenueDTO summary, ProcessedRevenueDomain domain) {

        summary.setValuesContainedInDTO(summary.getValuesContainedInDTO() + 1);

        /** Create Summary Row **/
        summary.setActualIntake(
                Precision.round(
                        summary.getActualIntake() +
                        domain.getActualIntake(),2));

        summary.setTapeIntake(
                Precision.round(
                summary.getTapeIntake() +
                        domain.getTapeIntake(),2));

        summary.setOverUnder(
                Precision.round(
                summary.getOverUnder() +
                        domain.getOverUnder(),2));

        summary.setTaxCount(
                Precision.round(
                summary.getTaxCount() +
                        domain.getTaxCount(),2));

        summary.setEnteredRevenueTaxTape(
                Precision.round(summary.getEnteredRevenueTaxTape() +
                        domain.getEnteredRevenue().getTaxTape(),2)
        );

        summary.setValuePerTransaction(
                Precision.round(summary.getValuePerTransaction() +
                        ((domain.getValuePerTransaction()-summary.getValuePerTransaction())/
                                summary.getValuesContainedInDTO())
                        ,2)
        );

        summary.setPercentageCard(
                Precision.round(summary.getPercentageCard() +
                                ((domain.getPercentageCard()-summary.getPercentageCard())/
                                        summary.getValuesContainedInDTO())
                        ,2)
        );

        summary.setPercentageCash(
                Precision.round(summary.getPercentageCash() +
                                ((domain.getPercentageCash()-summary.getPercentageCash())/
                                        summary.getValuesContainedInDTO())
                        ,2)
        );

        summary.setPercentageCheck(
                Precision.round(summary.getPercentageCheck() +
                                ((domain.getPercentageCheck()-summary.getPercentageCheck())/
                                        summary.getValuesContainedInDTO())
                        ,2)
        );

    }

    @Override
    public void onApplicationEvent(RevenueEnteredEvent revenueEnteredEvent) {
        LOGGER.debug("Handling event");
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


        //Total Daily Tape
        double tapeIntake = er.getCashTape() +
                er.getCheckTape() +
                er.getCardTape();


        //Over Under
        double overUnder = actualIntake - tapeIntake;

        //Tax Count
        double taxCount = (actualIntake) * (taxRate);

        //Round to 2 decimal places
        taxCount = Precision.round(taxCount, 2);

        double valuePerTranscation = actualIntake/er.getTransactionCount();
        valuePerTranscation = Precision.round(valuePerTranscation, 2);

        double percentageCard = er.getCardUnit()/actualIntake;
        percentageCard = Precision.round(percentageCard,2);

        double percentageCash = er.getCashCount()/actualIntake;
        percentageCash= Precision.round(percentageCash, 2);

        double percentageCheck = er.getCheckCount()/actualIntake;
        percentageCheck = Precision.round(percentageCheck, 2);

        ProcessedRevenueDomain processedRevenue = new ProcessedRevenueDomainBuilder()
                .setActualIntake(actualIntake)
                .setTapeIntake(tapeIntake)
                .setOverUnder(overUnder)
                .setTaxCount(taxCount)
                .setValuePerTransaction(valuePerTranscation)
                .setPercentageCard(percentageCard)
                .setPercentageCash(percentageCash)
                .setPercentageCheck(percentageCheck)
                .setEnteredRevenue(er)
                .createProcessedRevenueDomain();


        /**
         * See if we already have a processed Revenue for this date and location,
         * If so, update it with the new data by setting our new object's Id to the existing ID;
         */
        ProcessedRevenueDomain existing = processedRevenueDAO.findByLocationDomainAndDate(er.getLocation(), er.getDate());
        if(existing != null){
            processedRevenue.setId(existing.getId());
            //Maintain audit log
            processedRevenue.setCreatedBy(existing.getCreatedBy());
            processedRevenue.setCreationDate(existing.getCreationDate());
            LOGGER.warn("Processed Revenue Data is being overwritten by user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        }
        processedRevenueDAO.save(processedRevenue);
        LOGGER.info("Created or updated processed revenue entry for date: {} and location: {}", processedRevenue.getDate(), processedRevenue.getLocationDomain().getName());
    }


}
