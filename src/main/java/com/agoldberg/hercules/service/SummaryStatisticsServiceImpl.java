package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dao.ProcessedRevenueDAO;
import com.agoldberg.hercules.domain.ProcessedRevenueDomain;
import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.SummarySearchDTO;
import com.agoldberg.hercules.dto.SummaryStatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SummaryStatisticsServiceImpl implements SummaryStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryStatisticsService.class);
    @Autowired
    private ProcessedRevenueDAO processedRevenueDAO;

    @Autowired
    private StoreLocationServiceImpl storeLocationService;


    @Override
    public SummaryStatsDTO getSummaryStats(SummarySearchDTO search){
        LOGGER.debug("Generating summary statistics based on search params: " + search.getLocationName() + ", " +search.getDate());

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
        LOGGER.debug("Calculated current year MTD date: " + currentYearMTD);

        /** Make a new date at start of month **/
        Date currentYearStart = new Date(date.getYear(), date.getMonth(), 1);
        LOGGER.debug("Calculated current year start date: " + currentYearStart);


        /** Make a new date at the start of the month the year before **/
        Date priorYearStart = new Date(date.getYear()-1, date.getMonth(), 1);
        LOGGER.debug("Calculated prior year start date: " + priorYearStart);


        /** Make a new date at the current date in the prior year**/
        Date priorYearMTD = new Date(date.getYear()-1, date.getMonth(), date.getDate());
        LOGGER.debug("Calculated prior year MTD date: " + priorYearMTD);

        /**
         * Get the two lists of processed Data
         */
        List<ProcessedRevenueDomain> givenMTDList;
        List<ProcessedRevenueDomain> pastMTDList;

        /** Check if a location was included **/
        if(search.getLocationId() != null && search.getLocationId() != -1){
            LOGGER.info("Searching with location: " + search.getLocationName());
            StoreLocationDomain storeLocationDomain = storeLocationService.getStoreLocation(search.getLocationId());
            givenMTDList = processedRevenueDAO.findByLocationDomainAndDateBetween(storeLocationDomain, currentYearStart, currentYearMTD);
            pastMTDList = processedRevenueDAO.findByLocationDomainAndDateBetween(storeLocationDomain, priorYearStart, priorYearMTD);
        }else {
            LOGGER.info("Searching without location");
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
        LOGGER.info("Given Year MTD List Size: " + givenMTDList.size());
        LOGGER.debug("Given Year MTD Dollars: " + givenYearMTDDollars);

        double priorYearMTDDollars = 0;
        for(ProcessedRevenueDomain entries : pastMTDList){
            priorYearMTDDollars += entries.getActualPreTaxIntake();
        }
        LOGGER.info("Prior Year MTD List Size: " + pastMTDList.size());
        LOGGER.debug("Prior Year MTD Dollars: " + priorYearMTDDollars);

        return new SummaryStatsDTO(givenYearMTDDollars, priorYearMTDDollars);
    }


}
