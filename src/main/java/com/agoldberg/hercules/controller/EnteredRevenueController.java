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

    @Autowired
    private EnteredRevenueService enteredRevenueService;

    @Autowired
    private StoreLocationService storeLocationService;

    @Autowired
    private ProcessedRevenueService processedRevenueService;

    @Autowired
    private EnteredRevenueStaging staging;

    @GetMapping
    public ModelAndView showProcessedRevenues(Model model, @ModelAttribute(name = "search") EnteredSearchDTO search){
        EnteredSearchDTO searchDTO = new EnteredSearchDTO();

        if(search != null){
            searchDTO = search;
        }

        if(searchDTO.getLocationId() == null || searchDTO.getLocationId() == -1){
            searchDTO.setLocationId(null);
        }
        model.addAttribute("search", searchDTO);
        model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());
        return new ModelAndView("reporting/ProcessedRevenueList", "revenues", processedRevenueService.getProcessedRevenues(search));
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
        //model.addAttribute("enteredRevenue", new EnteredRevenueDTO());
        //Add a list of locations to the model.
        model.addAttribute("locationList", storeLocationService.getEnabledStoreLocations());

        /** Display Existing data if its there **/
        EnteredRevenueDTO dto;
        if((dto = staging.getEnteredRevenueDTO()) == null){
            dto = new EnteredRevenueDTO();
        }
        return new ModelAndView("enteredrevenue/EnteredRevenueForm", "enteredRevenue", dto);

    }

    @PostMapping("entry")
    public ModelAndView saveEnteredRevenueToSession(Model model, @Valid @ModelAttribute("enteredRevenue") EnteredRevenueDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //We still need the list
            model.addAttribute("locationList", storeLocationService.getEnabledStoreLocations());
            return new ModelAndView("enteredrevenue/EnteredRevenueForm", "enteredRevenue", dto);
        }else{
            dto.setLocationName(storeLocationService.getStoreName(dto.getLocationId()));
            staging.setEnteredRevenueDTO(dto);
        }
        return new ModelAndView("redirect:confirm");
    }

    @GetMapping("confirm")
    public ModelAndView displayEnteredRevenue(Model model){
        if(staging.isStaged()){
            //Check for existing entry
            EnteredRevenueDTO existing = enteredRevenueService.checkForExistingEntry(staging.getEnteredRevenueDTO());
            model.addAttribute("existing", existing);
            return new ModelAndView("enteredrevenue/ConfirmEnteredRevenue", "enteredRevenue", staging.getEnteredRevenueDTO());
        }else {
            throw new IllegalStateException("An entry has not been staged for confirmation.");
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
            throw new IllegalStateException("An entry has either not be staged or has already been confirmed.");
        }

        staging.reset();
        return new ModelAndView("enteredrevenue/SavedEnteredRevenue", "enteredRevenue", dto);
    }

}
