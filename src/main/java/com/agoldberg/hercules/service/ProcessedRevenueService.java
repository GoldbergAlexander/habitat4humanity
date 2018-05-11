package com.agoldberg.hercules.service;

import com.agoldberg.hercules.builder.ProcessedRevenueDomainBuilder;
import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.EnteredRevenueDomain;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.domain.UserDomain;
import com.agoldberg.hercules.dto.ProcessedRevenueDTO;
import com.agoldberg.hercules.event.RevenueEnteredEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessedRevenueService implements ApplicationListener<RevenueEnteredEvent>{

    @Autowired
    private ProcessedRevenueDAO processedRevenueDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${taxrate:.07}")
    private double taxRate;

    public List<ProcessedRevenueDTO> getProcessedRevenuesForActiveUser(){
        UserDomain user = userService.fetchActiveUser();
        return null;
    }

    public List<ProcessedRevenueDTO> getProcessedRevenues() {
        List<ProcessedRevenueDomain> domains = processedRevenueDAO.findAll();
        List<ProcessedRevenueDTO> dtos = new ArrayList<>();
        for(ProcessedRevenueDomain domain : domains){
            dtos.add(modelMapper.map(domain, ProcessedRevenueDTO.class));
        }
        return dtos;
    }

    @Override
    public void onApplicationEvent(RevenueEnteredEvent revenueEnteredEvent) {
        processRevenue(revenueEnteredEvent.getEnteredRevenue());
    }

    private void processRevenue(EnteredRevenueDomain er){
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

        processedRevenueDAO.save(processedRevenue);
    }


}
