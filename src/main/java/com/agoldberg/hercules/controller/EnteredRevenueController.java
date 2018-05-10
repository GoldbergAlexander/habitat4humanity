package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import com.agoldberg.hercules.service.EnteredRevenueService;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.session.EntryStaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/revenue")
public class EnteredRevenueController {

    @Autowired
    private EnteredRevenueService enteredRevenueService;

    @Autowired
    private StoreLocationService storeLocationService;

    @Autowired
    private EntryStaging entryStaging;

    /*
    Entry takes a certain set of steps and the system should only process the request if the occur in the correct order:
    1. System displays entry form
    2. User enters information into form
    3. System validates entry and display a confirmation page
    4. User confirms entered data.
    5. System processes entered data.

    The state is stored in the session and the user can walk between states at will as long as they move correctly.
     */

    @GetMapping("entry")
    public ModelAndView displayRevenueEntryForm(Model model){
        //model.addAttribute("enteredRevenue", new EnteredRevenueDTO());
        //Add a list of locations to the model.
        model.addAttribute("locationList", storeLocationService.getEnabledStoreLocations());

        return new ModelAndView("EnteredRevenueForm", "enteredRevenue", new EnteredRevenueDTO());

    }

    @PostMapping("entry")
    public ModelAndView saveEnteredRevenueToSession(Model model, @Valid @ModelAttribute("enteredRevenue") EnteredRevenueDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //We still need the list
            model.addAttribute("locationList", storeLocationService.getEnabledStoreLocations());
            return new ModelAndView("EnteredRevenueForm", "enteredRevnue", dto);
        }else{
            entryStaging.setEnteredRevenueDTO(dto);
        }
        return new ModelAndView("redirect:confirm");
    }

    @GetMapping("confirm")
    public ModelAndView displayEnteredRevenue(){
        if(entryStaging.isStaged()){
            return new ModelAndView("ConfirmEnteredRevenue", "enteredRevenue", entryStaging.getEnteredRevenueDTO());
        }else {
            throw new IllegalStateException("An entry has not been staged for confirmation.");
        }
    }

    @PostMapping("confirm")
    public ModelAndView confirmEnteredRevenue(){
        EnteredRevenueDTO dto;

        if(entryStaging.isStaged() && !entryStaging.isConfirmed()){
            entryStaging.setConfirmed(true);
            dto = enteredRevenueService.createRevenueEntry(entryStaging.getEnteredRevenueDTO());
        }else{
            throw new IllegalStateException("An entry has either not be staged or has already been confirmed.");
        }

        entryStaging.reset();
        return new ModelAndView("SavedEnteredRevenue", "enteredRevenue", dto);
    }

}
