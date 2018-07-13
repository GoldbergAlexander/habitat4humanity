package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.domain.StoreLocationDomain;
import com.agoldberg.hercules.dto.DepartmentRevenueDTO;
import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.service.DepartmentRevenueService;
import com.agoldberg.hercules.service.DepartmentService;
import com.agoldberg.hercules.service.StoreLocationService;
import com.agoldberg.hercules.session.DepartmentRevenueStaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/revenue/department")
public class DepartmentRevenueController {
    private static final String DEPARTMENT_REVENUE_MODEL = "departmentRevenue";
    private static final String DEPARTMENTS_MODEL = "departmentList";
    private static final String DEPARTMENT_REVENUE_FORM_VIEW = "departmentrevenue/DepartmentRevenueForm";
    private static final String CONFIRM_REDIRECT = "redirect:confirm";
    private static final String DEPARTMENT_REVENUE_CONFIRM_VIEW = "departmentrevenue/ConfirmDepartmentRevenue";
    private static final String STAGING_ERROR = "An entry has not been staged for confirmation.";
    private static final String STAGING_CONFIRM_ERROR = "An entry has either not be staged or has already been confirmed.";
    private static final String DEPARTMENT_REVENUE_SAVED_VIEW = "departmentrevenue/SavedDepartmentRevenue";

    @Autowired
    private DepartmentRevenueService departmentRevenueService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StoreLocationService locationService;

    @Autowired
    private DepartmentRevenueStaging staging;

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
    public ModelAndView displayRevenueEntryForm(Model model, @RequestParam(value = "locationId", required = false) Long locationId){
       //Get location list
        List<StoreLocationDTO> locations = locationService.getEnabledStoreLocations();

        //order the location menu to display the selected location
        if(locationId != null){
            for(StoreLocationDTO location : locations){
                if(location.getId().equals(locationId)){
                    Collections.swap(locations,locations.indexOf(location),0);
                    break;
                }
            }
        }

        /* Get the current year and add 5 to it (arbitrary, but in the future)*/
        Integer currentYear = Year.now().getValue() + 5;
        List<Integer> years = new ArrayList<>();
        /* Make an aaray list with 50 values */
        for(int i = 0; i < 50; i++){
            years.add(currentYear - i);
        }

        model.addAttribute("years", years);


        //Add a list of locations
        model.addAttribute("locations", locations);

        //Add a list of departments to the model.
        model.addAttribute(DEPARTMENTS_MODEL, departmentService.getEnabledDepartments(locationId));

        /** Display Existing data if its there **/
        DepartmentRevenueDTO dto;
        if((dto = staging.getDepartmentRevenueDTO()) == null){
            dto = new DepartmentRevenueDTO();

            /* Set the current year and date in the DTO */
            dto.setYear(Year.now());
            dto.setMonth(Month.from(LocalDate.now()));
        }

        return new ModelAndView(DEPARTMENT_REVENUE_FORM_VIEW, DEPARTMENT_REVENUE_MODEL, dto);

    }

    @PostMapping("entry")
    public ModelAndView saveEnteredRevenueToSession(Model model, @Valid @ModelAttribute(DEPARTMENT_REVENUE_MODEL) DepartmentRevenueDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //We still need the lists
            model.addAttribute("locations", locationService.getEnabledStoreLocations());
            model.addAttribute(DEPARTMENTS_MODEL, departmentService.getEnabledDepartments());

            return new ModelAndView(DEPARTMENT_REVENUE_FORM_VIEW, DEPARTMENT_REVENUE_MODEL, dto);
        }else{
            dto.setDepartmentName(departmentService.getDepartmentName(dto.getDepartmentId()));
            staging.setDepartmentRevenueDTO(dto);
        }
        return new ModelAndView(CONFIRM_REDIRECT);
    }

    @GetMapping("confirm")
    public ModelAndView displayEnteredRevenue(){
        if(staging.isStaged()){
            return new ModelAndView(DEPARTMENT_REVENUE_CONFIRM_VIEW, DEPARTMENT_REVENUE_MODEL, staging.getDepartmentRevenueDTO());
        }else {
            throw new IllegalStateException(STAGING_ERROR);
        }
    }

    @PostMapping("confirm")
    public ModelAndView confirmEnteredRevenue(){
        DepartmentRevenueDTO dto;

        if(staging.isStaged() && !staging.isConfirmed()){
            staging.setConfirmed(true);
            dto = departmentRevenueService.createRevenueEntry(staging.getDepartmentRevenueDTO());
        }else{
            staging.reset();
            throw new IllegalStateException(STAGING_CONFIRM_ERROR);
        }

        staging.reset();
        return new ModelAndView(DEPARTMENT_REVENUE_SAVED_VIEW, DEPARTMENT_REVENUE_MODEL, dto);
    }
}
