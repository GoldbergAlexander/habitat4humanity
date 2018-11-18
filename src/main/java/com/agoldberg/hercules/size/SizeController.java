package com.agoldberg.hercules.size;

import com.agoldberg.hercules.department.DepartmentService;
import com.agoldberg.hercules.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/size")
public class SizeController {
    @Autowired
    private SizeService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ModelAndView showSizes(Model model){
        model.addAttribute("newSize", new SizeDTO());
        return new ModelAndView("size/SizeList","sizes",service.getSizes());
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        model.addAttribute("departments", departmentService.getEnabledDepartments());
        return new ModelAndView("size/CreateSizeForm","size", new SizeDTO());
    }

    @PostMapping("create")
    public ModelAndView createSize(Model model, @Valid @ModelAttribute("size") SizeDTO size, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.createSize(size);
            return new ModelAndView("redirect:/size");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute("departments", departmentService.getEnabledDepartments());
            model.addAttribute(size);
        }
        return new ModelAndView("size/CreateSizeForm","store", size);
    }

    @GetMapping("{size_id}")
    public ModelAndView showModifyForm(Model model,@PathVariable("size_id") Long id){
        model.addAttribute("stores", storeService.getEnabledStores());
        model.addAttribute("departments", departmentService.getEnabledDepartments());
        return new ModelAndView("size/ModifySizeForm","size",service.getSize(id));
    }

    @PostMapping("{size_id}")
    public ModelAndView modifySize(Model model, @PathVariable("size_id") Long id, @Valid @ModelAttribute("size") SizeDTO size, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.modifySize(size);
            return new ModelAndView("redirect:/size");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute("departments", departmentService.getEnabledDepartments());
            model.addAttribute(size);
        }
        return new ModelAndView("size/ModifySizeForm","size",service.getSize(id));
    }

    @PostMapping("delete")
    public String deleteStore(Model model, @ModelAttribute("newSize") SizeDTO size){
        service.deleteSize(size);
        return "redirect:/size";
    }
}
