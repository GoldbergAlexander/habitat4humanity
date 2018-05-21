package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.DepartmentDTO;
import com.agoldberg.hercules.service.DepartmentService;
import com.agoldberg.hercules.service.StoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("department")
public class DepartmentController {
    public static final String LOCATIONS_MODEL = "locations";
    public static final String DEPARTMENT_REDIRECT = "redirect:/department";
    public static final String NEW_DEPARTMENT_MODEL = "newDepartment";
    public static final String DEPARTMENT_LIST_VIEW = "department/DepartmentList";
    public static final String DEPARTMENTS_MODEL = "departments";
    public static final String DEPARTMENT_FORM_VIEW = "department/DepartmentForm";
    public static final String DEPARTMENT_MODEL = "department";
    @Autowired
    private StoreLocationService storeLocationService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping
    public ModelAndView showDepartments(Model model){
        model.addAttribute(NEW_DEPARTMENT_MODEL, new DepartmentDTO());
        model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
        return new ModelAndView(DEPARTMENT_LIST_VIEW, DEPARTMENTS_MODEL, departmentService.getDepartments());
    }

    @GetMapping("{department_id}")
    public ModelAndView showDepartmentForm(@PathVariable("department_id") Long departmentId, Model model){
        model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
        return new ModelAndView(DEPARTMENT_FORM_VIEW, DEPARTMENT_MODEL, departmentService.getDepartmentDTO(departmentId));

    }

    @PostMapping("{department_id}")
    public ModelAndView modifyDepartment(@PathVariable("department_id") Long departmentId, Model model, @Valid @ModelAttribute("department") DepartmentDTO department, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            departmentService.modifyDepartment(department);
            return new ModelAndView(DEPARTMENT_REDIRECT);
        }else{
            model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
            return new ModelAndView(DEPARTMENT_FORM_VIEW, DEPARTMENT_MODEL, department);
        }
    }

    @PostMapping("create")
    public ModelAndView createDepartment(Model model, @Valid @ModelAttribute(NEW_DEPARTMENT_MODEL) DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()) {
            departmentService.createDepartment(departmentDTO);
            return new ModelAndView(DEPARTMENT_REDIRECT);

        }else {
            model.addAttribute(LOCATIONS_MODEL, storeLocationService.getEnabledStoreLocations());
            return new ModelAndView(DEPARTMENT_LIST_VIEW, NEW_DEPARTMENT_MODEL, departmentDTO);
        }
    }


    @PostMapping("enable")
    public String toggleEnable(@ModelAttribute(NEW_DEPARTMENT_MODEL) DepartmentDTO dto){
        departmentService.toggleDepartmentEnabled(dto.getId());
        return DEPARTMENT_REDIRECT;
    }

    @PostMapping("delete")
    public String deleteDepartment(Model model, @Valid @ModelAttribute(NEW_DEPARTMENT_MODEL) DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        departmentService.deleteDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute(model);
        return DEPARTMENT_REDIRECT;
    }
}
