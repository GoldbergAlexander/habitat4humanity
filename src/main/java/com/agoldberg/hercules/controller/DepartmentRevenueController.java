package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.DepartmentRevenueDTO;
import com.agoldberg.hercules.service.DepartmentRevenueService;
import com.agoldberg.hercules.service.DepartmentService;
import com.agoldberg.hercules.session.DepartmentRevenueStaging;
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
@RequestMapping("/departmentrevenue")
public class DepartmentRevenueController {
    @Autowired
    private DepartmentRevenueService departmentRevenueService;

    @Autowired
    private DepartmentService departmentService;

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
    public ModelAndView displayRevenueEntryForm(Model model){
        //model.addAttribute("enteredRevenue", new EnteredRevenueDTO());
        //Add a list of locations to the model.
        model.addAttribute("departmentList", departmentService.getEnabledDepartments());

        return new ModelAndView("DepartmentRevenueForm", "departmentRevenue", new DepartmentRevenueDTO());

    }

    @PostMapping("entry")
    public ModelAndView saveEnteredRevenueToSession(Model model, @Valid @ModelAttribute("departmentRevenue") DepartmentRevenueDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //We still need the list
            model.addAttribute("departmentList", departmentService.getEnabledDepartments());
            return new ModelAndView("DepartmentRevenueForm", "departmentRevenue", dto);
        }else{
            dto.setDepartmentName(departmentService.getDepartmentName(dto.getDepartmentId()));
            staging.setDepartmentRevenueDTO(dto);
        }
        return new ModelAndView("redirect:confirm");
    }

    @GetMapping("confirm")
    public ModelAndView displayEnteredRevenue(){
        if(staging.isStaged()){
            return new ModelAndView("ConfirmDepartmentRevenue", "departmentRevenue", staging.getDepartmentRevenueDTO());
        }else {
            throw new IllegalStateException("An entry has not been staged for confirmation.");
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
            throw new IllegalStateException("An entry has either not be staged or has already been confirmed.");
        }

        staging.reset();
        return new ModelAndView("SavedDepartmentRevenue", "departmentRevenue", dto);
    }
}
