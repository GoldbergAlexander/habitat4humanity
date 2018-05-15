package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.SummarySearchDTO;
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

    @Autowired
    private StoreLocationService storeLocationService;


    public SummaryStatsDTO getSummaryStats(SummarySearchDTO search){
        /**
         * Define the search ranges for the given month, and the same month one year ago;
         */

        /**
         * Given Year Month
         */

        /** Get the date from the search object **/
        Date date = search.getDate();

        /** Set up the date objects **/
        //CurrentYearMTD is just the given date
        Date currentYearMTD = date;

        /** Make a new date at start of month **/
        Date currentYearStart = new Date(date.getYear(), date.getMonth(), 1);


        /** Make a new date at the start of the month the year before **/
        Date priorYearStart = new Date(date.getYear()-1, date.getMonth(), 1);

        /** Make a new date at the current date in the prior year**/
        Date priorYearMTD = new Date(date.getYear()-1, date.getMonth(), date.getDate());

        /**
         * Get the two lists of processed Data
         */
        List<ProcessedRevenueDomain> givenMTDList;
        List<ProcessedRevenueDomain> pastMTDList;

        /** Check if a location was included **/
        if(search.getLocationId() != null && search.getLocationId() != -1){
            StoreLocationDomain storeLocationDomain = storeLocationService.getStoreLocation(search.getLocationId());
            givenMTDList = processedRevenueDAO.findByLocationDomainAndDateBetween(storeLocationDomain, currentYearStart, currentYearMTD);
            pastMTDList = processedRevenueDAO.findByLocationDomainAndDateBetween(storeLocationDomain, priorYearStart, priorYearMTD);
        }else {
            givenMTDList = processedRevenueDAO.findByDateBetween(currentYearStart, currentYearMTD);
            pastMTDList = processedRevenueDAO.findByDateBetween(priorYearStart, priorYearMTD);
        }

        /**
         * Add up all the months -- specific value given by client
         */
        double givenYearMTDDollars = 0;
        for(ProcessedRevenueDomain entries : givenMTDList){
            givenYearMTDDollars += entries.getActualPreTaxIntake();
        }

        double priorYearMTDDollars = 0;
        for(ProcessedRevenueDomain entries : pastMTDList){
            priorYearMTDDollars += entries.getActualPreTaxIntake();
        }


        return new SummaryStatsDTO(givenYearMTDDollars, priorYearMTDDollars);
    }


}
