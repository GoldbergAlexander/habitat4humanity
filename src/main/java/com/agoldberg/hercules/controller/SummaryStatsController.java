package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.EnteredSearchDTO;
import com.agoldberg.hercules.dto.SummarySearchDTO;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.service.SummaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/summarystats")
public class SummaryStatsController {

    @Autowired
    private SummaryStatisticsService summaryStatisticsService;

    @Autowired
    private StoreLocationService storeLocationService;

    @RequestMapping
    public ModelAndView displayStatsForDay(Model model, @ModelAttribute("search") SummarySearchDTO search){
        SummarySearchDTO searchDTO = new SummarySearchDTO();

        if(search != null){
            searchDTO = search;
        }

        if(searchDTO.getLocationId() == null || searchDTO.getLocationId() == -1){
            searchDTO.setLocationId(null);
        }
        model.addAttribute("search", searchDTO);
        model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());

        return new ModelAndView("reporting/SummaryStatsDisplay", "stats", summaryStatisticsService.getSummaryStats(searchDTO));
    }

}
