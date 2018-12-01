package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.EnteredRevenueBatchDTO;
import com.agoldberg.hercules.service.EnteredRevenueBatchService;
import com.agoldberg.hercules.service.EnteredRevenueService;
import com.agoldberg.hercules.store.StoreService;
import com.agoldberg.hercules.session.EnteredRevenueBatchStaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//@RequestMapping("/revenue/daily/batch")
public class EnteredRevenueBatchController {

    @Autowired
    EnteredRevenueBatchStaging staging;

    @Autowired
    StoreService locationService;

    @Autowired
    EnteredRevenueBatchService batchService;

    @Autowired
    EnteredRevenueService enteredRevenueService;

    @GetMapping("entry")
    public ModelAndView displayRevenueEntryBatchForm(Model model){
        model.addAttribute("locations", locationService.getEnabledStores());

        EnteredRevenueBatchDTO dto;
        if((dto = staging.getDTO()) == null){
            dto = new EnteredRevenueBatchDTO();
        }

        return new ModelAndView("enteredrevenuebatch/BatchEntryForm", "batchDTO", dto);
    }

    @PostMapping("entry")
    public ModelAndView confirmEnteredRevenueBatchData(Model model, @ModelAttribute("batchDTO") EnteredRevenueBatchDTO enteredRevenueBatchDTO){
        staging.setDTO(batchService.processBatchData(enteredRevenueBatchDTO));
        return new ModelAndView("enteredrevenuebatch/BatchEntryDisplay", "batchDTO", staging.getDTO());
    }

    @PostMapping("confirm")
    public ModelAndView saveEnteredRevenueBatchData(Model model, @ModelAttribute("batchDTO") EnteredRevenueBatchDTO enteredRevenueBatchDTO){
        staging.getDTO().setEnteredRevenueDTOList(
                enteredRevenueService.createRevenueEntry(
                        staging.getDTO().getEnteredRevenueDTOList()));
        return new ModelAndView("enteredrevenuebatch/BatchEntryConfirm","batchDTO", staging.getDTO());
    }

}
