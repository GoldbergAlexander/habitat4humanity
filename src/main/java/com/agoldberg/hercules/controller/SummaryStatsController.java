package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.service.SummaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/summarystats")
public class SummaryStatsController {

    @Autowired
    private SummaryStatisticsService summaryStatisticsService;

    @RequestMapping
    public ModelAndView displayStatsForDay(@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "dd/mm/yyyy") Date date){
        if(date == null){
            date = new Date();
        }
        return new ModelAndView("reporting/SummaryStatsDisplay", "stats", summaryStatisticsService.getSummaryStats(date));
    }

}
