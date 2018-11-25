package com.agoldberg.hercules.departmentrevenue;

import com.agoldberg.hercules.department.DepartmentService;
import com.agoldberg.hercules.size.SizeService;
import com.agoldberg.hercules.store.StoreService;
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
@RequestMapping("departmentrevenue")
public class DepartmentRevenueController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRevenueService service;

    @Autowired
    private SizeService sizeService;

    @GetMapping("create")
    public ModelAndView showCreateDepartmentRevenueEntry(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        model.addAttribute("departments", departmentService.getEnabledDepartments());
        DepartmentRevenueDTO departmentRevenue = new DepartmentRevenueDTO();
        return new ModelAndView("departmentrevenue/CreateDepartmentRevenueForm", "departmentRevenue", departmentRevenue);
    }

    @PostMapping("create")
    public ModelAndView CreateDepartmentRevenueEntry(Model model, @Valid @ModelAttribute("departmentRevenue") DepartmentRevenueDTO departmentRevenue, BindingResult result){
        if(!result.hasErrors()){
            model.addAttribute("oldDepartmentRevenue", service.getExistingDepartmentRevenue(departmentRevenue));
            departmentRevenue = service.createDepartmentRevenueEntry(departmentRevenue);
            model.addAttribute("size", sizeService.getSizeForStoreDepartmentDate(departmentRevenue.getStoreId(), departmentRevenue.getDepartmentId(), departmentRevenue.getDate()));
            return new ModelAndView("departmentrevenue/DepartmentRevenueConfirmation", "departmentRevenue", departmentRevenue);
        }else{
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute("departments", departmentService.getEnabledDepartments());
            return new ModelAndView("departmentrevenue/CreateDepartmentRevenueForm", "departmentRevenue", departmentRevenue);
        }
    }

    @GetMapping
    public ModelAndView showDepartmentRevenueEntries(Model model, @ModelAttribute("search") SearchDTO dto, BindingResult result){
        if(dto == null){
            dto = new SearchDTO();
        }
        model.addAttribute("search", dto);
        model.addAttribute("stores", storeService.getEnabledStores());
        model.addAttribute("departments", departmentService.getEnabledDepartments());
        return new ModelAndView("departmentrevenue/DepartmentRevenueList", "departmentRevenues", service.searchDepartmentEntries(dto));
    }

//    @GetMapping
//    public ModelAndView showDepartmentRevenueEntries(Model model){
//        model.addAttribute("search", new SearchDTO());
//        model.addAttribute("stores", storeService.getEnabledStores());
//        model.addAttribute("departments", departmentService.getEnabledDepartments());
//        return new ModelAndView("departmentrevenue/DepartmentRevenueList","departmentRevenues", service.getRecent());
//    }



}
