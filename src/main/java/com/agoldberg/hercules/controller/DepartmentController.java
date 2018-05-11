package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.DepartmentDTO;
import com.agoldberg.hercules.service.DepartmentService;
import com.agoldberg.hercules.service.StoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return new ModelAndView("DepartmentList", "departments", departmentService.getDepartments());
    }

    @PostMapping("create")
    public String createDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        departmentService.createDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/department";
    }


    @PostMapping("modify")
    public String modifyDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        departmentService.modifyDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/department";
    }

    @PostMapping("delete")
    public String deleteDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        departmentService.deleteDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/department";
    }
}
