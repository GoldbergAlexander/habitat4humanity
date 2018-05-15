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
    @Autowired
    private StoreLocationService storeLocationService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping
    public ModelAndView showDepartments(Model model){
        model.addAttribute("newDepartment", new DepartmentDTO());
        model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());
        return new ModelAndView("department/DepartmentList", "departments", departmentService.getDepartments());
    }

    @GetMapping("{department_id}")
    public ModelAndView showDepartmentForm(@PathVariable("department_id") Long departmentId, Model model){
        model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());
        return new ModelAndView("department/DepartmentForm","department", departmentService.getDepartmentDTO(departmentId));

    }

    @PostMapping("{department_id}")
    public ModelAndView modifyDepartment(@PathVariable("department_id") Long departmentId, Model model, @Valid @ModelAttribute("department") DepartmentDTO department, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            departmentService.modifyDepartment(department);
            return new ModelAndView("redirect:/department");
        }else{
            model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());
            return new ModelAndView("department/DepartmentForm", "department", department);
        }
    }

    @PostMapping("create")
    public ModelAndView createDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()) {
            departmentService.createDepartment(departmentDTO);
            return new ModelAndView("redirect:/department");

        }else {
            model.addAttribute("locations", storeLocationService.getEnabledStoreLocations());
            return new ModelAndView("department/DepartmentList", "newDepartment", departmentDTO);
        }
    }

    @PostMapping("enable")
    public String toggleEnable(@ModelAttribute("newDepartment") DepartmentDTO dto){
        departmentService.toggleDepartmentEnabled(dto.getId());
        return "redirect:/department";
    }

    @PostMapping("delete")
    public String deleteDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        departmentService.deleteDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/department";
    }
}
