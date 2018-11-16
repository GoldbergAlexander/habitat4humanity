package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.SummarySearchDTO;
import com.agoldberg.hercules.store.StoreService;
import com.agoldberg.hercules.service.SummaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/summarystats")
public class SummaryStatsController {

    private static final String STATS = "stats";
    private static final String SEARCH = "search";
    private static final String LOCATIONS = "locations";
    private static final String SUMMARY_STATS_VIEW = "reporting/SummaryStatsDisplay";

    @Autowired
    private SummaryStatisticsService summaryStatisticsService;

    @Autowired
    private StoreService storeLocationService;

    @RequestMapping
    public ModelAndView displayStatsForDay(Model model, @ModelAttribute(SEARCH) SummarySearchDTO search){
        SummarySearchDTO searchDTO = new SummarySearchDTO();

        if(search != null){
            searchDTO = search;
        }

        if(searchDTO.getLocationId() == null || searchDTO.getLocationId() == -1){
            searchDTO.setLocationId(null);
        }
        model.addAttribute(SEARCH, searchDTO);
        model.addAttribute(LOCATIONS, storeLocationService.getEnabledStores());

        return new ModelAndView(SUMMARY_STATS_VIEW, STATS, summaryStatisticsService.getSummaryStats(searchDTO));
    }

}
