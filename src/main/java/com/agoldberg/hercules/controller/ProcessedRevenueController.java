package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.service.ProcessedRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/revenue")
public class ProcessedRevenueController {

    @Autowired
    private ProcessedRevenueService processedRevenueService;

    @GetMapping("processed")
    public ModelAndView showProcessedRevenues(){
        return new ModelAndView("ProcessedRevenueList", "revenues", processedRevenueService.getProcessedRevenues());
    }

}
