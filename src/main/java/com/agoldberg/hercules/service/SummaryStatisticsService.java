package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.dto.SummaryStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class SummaryStatisticsService {
    @Autowired
    private ProcessedRevenueDAO processedRevenueDAO;

    public SummaryStatsDTO getSummaryStatsForCurrentMonth(){
        return getSummaryStats(new Date());
    }

    public SummaryStatsDTO getSummaryStats(Date date){
        /**
         * Define the search ranges for the given month, and the same month one year ago;
         */

        /**
         * Given Year Month
         */
        int currentDay = date.getDay();
        YearMonth givenYearMonth = YearMonth.of(date.getYear(), date.getMonth()+1);
        LocalDate firstOfGivenMonth = givenYearMonth.atDay(1);
        LocalDate currentOfGivenMonth = givenYearMonth.atDay(currentDay);
        LocalDate lastOfGivenMonth = givenYearMonth.atEndOfMonth();

        Date firstOfGivenMonthDate = java.sql.Date.valueOf(firstOfGivenMonth);
        Date currentOfGivenMonthDate = java.sql.Date.valueOf(currentOfGivenMonth);
        Date lastOfGivenMonthDate = java.sql.Date.valueOf(lastOfGivenMonth);

        /**
         * Past Year Month
         */
        YearMonth pastYearMonth = givenYearMonth.minusYears(1);
        LocalDate firstOfPastMonth = pastYearMonth.atDay(1);
        LocalDate currentOfPastMonth = pastYearMonth.atDay(currentDay);
        LocalDate lastOfPastMonth = pastYearMonth.atEndOfMonth();

        Date firstOfPastMonthDate = java.sql.Date.valueOf(firstOfPastMonth);
        Date currentOfPastMonthDate = java.sql.Date.valueOf(currentOfPastMonth);
        Date lastOfPastMonthDate = java.sql.Date.valueOf(lastOfPastMonth);


        /**
         * Get the two lists of processed Data
         */
        List<ProcessedRevenueDomain> givenMTDList = processedRevenueDAO.findByDateBetween(firstOfGivenMonthDate, currentOfGivenMonthDate);
        List<ProcessedRevenueDomain> pastMTDList = processedRevenueDAO.findByDateBetween(firstOfPastMonthDate, currentOfPastMonthDate);

        SummaryStatsDTO summaryStatsDTO = new SummaryStatsDTO();

        /**
         * Add up all the months -- specific value given by client
         */
        double givenYearMTD = 0;
        for(ProcessedRevenueDomain entries : givenMTDList){
            givenYearMTD += entries.getActualPreTaxIntake();
        }

        double priorYearMTD = 0;
        for(ProcessedRevenueDomain entries : pastMTDList){
            priorYearMTD += entries.getActualPreTaxIntake();
        }

        return new SummaryStatsDTO(givenYearMTD, priorYearMTD);


    }


}
