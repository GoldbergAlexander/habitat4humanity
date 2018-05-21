package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import com.agoldberg.hercules.dto.EnteredSearchDTO;
import com.agoldberg.hercules.service.EnteredRevenueService;
import com.agoldberg.hercules.service.ProcessedRevenueService;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.session.EnteredRevenueStaging;
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
@RequestMapping("/revenue/daily")
public class EnteredRevenueController {

    private static final String ENTERED_REVENUE_MODEL = "enteredRevenue";
    private static final String SEARCH_MODEL = "search";
    private static final String LOCATIONS_MODEL = "locations";
    private static final String REPORTING_PROCESSED_VIEW = "reporting/ProcessedRevenueList";
    private static final String REVENUES_MODEL = "revenues";
    private static final String ENTERED_REVENUE_FORM_VIEW = "enteredrevenue/EnteredRevenueForm";
    private static final String CONFIRM_REDIRECT = "redirect:confirm";
    private static final String EXISTING_MODEL = "existing";
    private static final String ENTERED_REVENUE_CONFIRM_VIEW = "enteredrevenue/ConfirmEnteredRevenue";
    private static final String STAGING_ERROR = "An entry has not been staged for confirmation.";
    private static final String STAGING_CONFIRM_ERROR = "An entry has either not be staged or has already been confirmed.";
    private static final String ENTERED_REVENUE_SAVED_VIEW = "enteredrevenue/SavedEnteredRevenue";

    @Autowired
    private EnteredRevenueService enteredRevenueService;

    @Autowired
    private StoreLocationService storeLocationService;

    @Autowired
    private ProcessedRevenueService processedRevenueService;

    @Autowired
    private EnteredRevenueStaging staging;

    @GetMapping
    public ModelAndView showProcessedRevenues(Model model, @ModelAttribute(name = SEARCH_MODEL) EnteredSearchDTO search){
        EnteredSearchDTO searchDTO = new EnteredSearchDTO();

        if(search != null){
            searchDTO = search;
        }

        if(searchDTO.getLocationId() == null || searchDTO.getLocationId() == -1){
            searchDTO.setLocationId(null);
        }
        model.addAttribute(SEARCH_MODEL, searchDTO);
        model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
        return new ModelAndView(REPORTING_PROCESSED_VIEW, REVENUES_MODEL, processedRevenueService.getProcessedRevenues(search));
    }

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
        //Add a list of locations to the model.
        model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());

        /** Display Existing data if its there **/
        EnteredRevenueDTO dto;
        if((dto = staging.getEnteredRevenueDTO()) == null){
            dto = new EnteredRevenueDTO();
        }
        return new ModelAndView(ENTERED_REVENUE_FORM_VIEW, ENTERED_REVENUE_MODEL, dto);

    }

    @PostMapping("entry")
    public ModelAndView saveEnteredRevenueToSession(Model model, @Valid @ModelAttribute(ENTERED_REVENUE_MODEL) EnteredRevenueDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //We still need the list
            model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
            return new ModelAndView(ENTERED_REVENUE_FORM_VIEW, ENTERED_REVENUE_MODEL, dto);
        }else{
            dto.setLocationName(storeLocationService.getStoreName(dto.getLocationId()));
            staging.setEnteredRevenueDTO(dto);
        }
        return new ModelAndView(CONFIRM_REDIRECT);
    }

    @GetMapping("confirm")
    public ModelAndView displayEnteredRevenue(Model model){
        if(staging.isStaged()){
            //Check for existing entry
            EnteredRevenueDTO existing = enteredRevenueService.checkForExistingEntry(staging.getEnteredRevenueDTO());
            model.addAttribute(EXISTING_MODEL, existing);
            return new ModelAndView(ENTERED_REVENUE_CONFIRM_VIEW, ENTERED_REVENUE_MODEL, staging.getEnteredRevenueDTO());
        }else {
            throw new IllegalStateException(STAGING_ERROR);
        }
    }

    @PostMapping("confirm")
    public ModelAndView confirmEnteredRevenue(){
        EnteredRevenueDTO dto;

        if(staging.isStaged() && !staging.isConfirmed()){
            staging.setConfirmed(true);
            dto = enteredRevenueService.createRevenueEntry(staging.getEnteredRevenueDTO());
        }else{
            staging.reset();
            throw new IllegalStateException(STAGING_CONFIRM_ERROR);
        }

        staging.reset();
        return new ModelAndView(ENTERED_REVENUE_SAVED_VIEW, ENTERED_REVENUE_MODEL, dto);
    }

}
