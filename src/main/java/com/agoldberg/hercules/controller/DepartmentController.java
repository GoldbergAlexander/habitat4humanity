package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.DepartmentDTO;
import com.agoldberg.hercules.service.DepartmentService;
import com.agoldberg.hercules.service.StoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView createDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO){
        departmentService.createDepartment(departmentDTO);
        return showDepartments(model);
    }


    @PostMapping("modify")
    public ModelAndView modifyDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO){
        departmentService.modifyDepartment(departmentDTO);
        return showDepartments(model);
    }

    @PostMapping("delete")
    public ModelAndView deleteDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO departmentDTO){
        departmentService.deleteDepartment(departmentDTO);
        return showDepartments(model);
    }
}
