package com.agoldberg.hercules.tax;

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
@RequestMapping("/tax")
public class TaxController {
    @Autowired
    private TaxService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ModelAndView showTaxes(Model model){
        model.addAttribute("newTax", new TaxDTO());
        List<TaxDTO> taxes = service.getTaxes();
        Map<String, List<TaxDTO>> map = new HashMap<>();
        map.put("data", taxes);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }

        return new ModelAndView("tax/TaxList","taxes",taxes);
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("tax/CreateTaxForm","tax", new TaxDTO());
    }

    @PostMapping("create")
    public ModelAndView createTax(Model model, @Valid @ModelAttribute("tax") TaxDTO tax, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.createTax(tax);
            return new ModelAndView("redirect:/tax");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute(tax);
        }
        return new ModelAndView("tax/CreateTaxForm","store", tax);
    }

    @GetMapping("{tax_id}")
    public ModelAndView showModifyForm(Model model,@PathVariable("tax_id") Long id){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("tax/ModifyTaxForm","tax",service.getTax(id));
    }

    @PostMapping("{tax_id}")
    public ModelAndView modifyTax(Model model, @PathVariable("tax_id") Long id, @Valid @ModelAttribute("tax") TaxDTO tax, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.modifyTax(tax);
            return new ModelAndView("redirect:/tax");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute(tax);
        }
        return new ModelAndView("tax/ModifyTaxForm","tax",service.getTax(id));
    }

    @PostMapping("delete")
    public String deleteStore(Model model, @ModelAttribute("newTax") TaxDTO tax){
        service.deleteTax(tax);
        return "redirect:/tax";
    }
}
