package com.agoldberg.hercules.size;

import com.agoldberg.hercules.department.DepartmentService;
import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.store.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/size")
public class SizeController {
    @Autowired
    private SizeService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ModelAndView showSizes(Model model){
        model.addAttribute("newSize", new SizeDTO());
        List<SizeDTO> sizes = service.getSizes();
        Map<String, List<SizeDTO>> map = new HashMap<>();
        map.put("data", sizes);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("size/SizeList","sizes",sizes);
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
