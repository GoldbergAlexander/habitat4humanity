package com.agoldberg.hercules.dailyentry;

import com.agoldberg.hercules.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("dailyentry")
public class DailyEntryController {
    @Autowired
    private DailyEntryService service;

    @Autowired
    private StoreService storeService;


    @GetMapping("create")
    public ModelAndView showCreateDailyEntry(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("dailyentry/CreateDailyEntryForm", "entry", new DailyEntryDTO());
    }

    @GetMapping("modify/{id}")
    public ModelAndView showModifyDailyEntry(Model model, @PathVariable("id") Long id){
        model.addAttribute("stores", storeService.getEnabledStores());
        DailyEntryDTO dto = service.getDailyEntry(id);
        return new ModelAndView("dailyentry/CreateDailyEntryForm", "entry", dto);
    }

    @PostMapping("create")
    public ModelAndView createDailyEntry(Model model, @Valid @ModelAttribute("entry") DailyEntryDTO entry, BindingResult result){
        if(!result.hasErrors()){
            model.addAttribute("oldEntry", service.getExistingDailyEntry(entry));
            DailyEntryExtendedAnalysisDTO entryExtended = service.createDailyEntry(entry);
            return new ModelAndView("dailyentry/DailyEntryConfirmation", "entry", entryExtended);
        }else{
            model.addAttribute("stores", storeService.getEnabledStores());
            return new ModelAndView("dailyentry/CreateDailyEntryForm", "entry", entry);
        }
    }

    @GetMapping
    public ModelAndView showDailyEntries(Model model, @ModelAttribute("search") SearchDTO search, BindingResult result){
        if(search.getStoreId() == null){
            Date end = new Date();
            Date start = new Date();
            start.setDate(1);
            search.setStart(start);
            search.setEnd(end);
            search.setLod(LOD.ENTRY);
        }
        model.addAttribute("search", search);
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("dailyentry/DailyEntryList", "entries", service.searchDailyEntry(search));
    }
}
