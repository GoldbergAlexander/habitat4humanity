package com.agoldberg.hercules.department;

import com.agoldberg.hercules.size.SizeDTO;
import com.agoldberg.hercules.size.SizeService;
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
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService service;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping
    public ModelAndView showDepartments(Model model){
        model.addAttribute("newDepartment", new DepartmentDTO());
        List<DepartmentDTO> departments = service.getDepartments();
        return new ModelAndView("department/DepartmentList","departments", departments);
    }

    @GetMapping("{department_id}/size")
    public ModelAndView showSizesForDepartment(Model model, @PathVariable("department_id") Long id){
        model.addAttribute("department", service.getDepartment(id));
        List<SizeDTO> sizes = sizeService.getSizesForDepartment(id);
        Map<String, List<SizeDTO>> map = new HashMap<>();
        map.put("data", sizes);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("size/SizeList", "sizes", sizes);
    }

    @GetMapping("{department_id}")
    public ModelAndView showDepartmentForm(@PathVariable("department_id") Long id){
        return new ModelAndView("department/ModifyDepartmentForm", "department", service.getDepartmentDTO(id));
    }

    @PostMapping("{department_id}")
    public ModelAndView modifyLocation(@PathVariable("department_id") Long id, @Valid @ModelAttribute("department") DepartmentDTO department, BindingResult bindingResult){
        if(department.getId() == null || !id.equals(department.getId())){
            bindingResult.reject("Bad ID");
        }
        if(!bindingResult.hasErrors()){
            service.modifyDepartment(department);
            return new ModelAndView("redirect:/department");
        }else{
            return new ModelAndView("department/ModifyDepartmentForm", "department", department);
        }
    }

    @PostMapping("enable")
    public String toggleEnabled(@ModelAttribute("newDepartment") DepartmentDTO department){
        service.toggleDepartmentEnabled(department.getId());
        return "redirect:/department";
    }

    @GetMapping("create")
    public ModelAndView showCreateDepartmentForm(){
        return new ModelAndView("department/CreateDepartmentForm", "department", new DepartmentDTO());
    }

    @PostMapping("create")
    public ModelAndView createDepartment(@Valid @ModelAttribute("department") DepartmentDTO department, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            service.createDepartment(department);
            return new ModelAndView("redirect:/department");
        }else {
            return new ModelAndView("department/CreateDepartmentForm", "department", department);
        }
    }

    @PostMapping("delete")
    public String deleteDepartment(Model model, @Valid @ModelAttribute("newDepartment") DepartmentDTO department){
        service.deleteDepartment(department);
        return "redirect:/department";
    }
}
