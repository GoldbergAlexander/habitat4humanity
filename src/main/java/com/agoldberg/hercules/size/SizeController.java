package com.agoldberg.hercules.size;

import com.agoldberg.hercules.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/tax")
public class SizeController {
    @Autowired
    private SizeService service;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ModelAndView showTaxes(Model model){
        model.addAttribute("newTax", new SizeDTO());
        return new ModelAndView("tax/TaxList","taxes",service.getTaxes());
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("tax/CreateTaxForm","tax", new SizeDTO());
    }

    @PostMapping("create")
    public ModelAndView createTax(Model model, @Valid @ModelAttribute("tax") SizeDTO tax, BindingResult bindingResult){
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
    public ModelAndView modifyTax(Model model, @PathVariable("tax_id") Long id, @Valid @ModelAttribute("tax") SizeDTO tax, BindingResult bindingResult){
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
    public String deleteStore(Model model, @ModelAttribute("newTax") SizeDTO tax){
        service.deleteTax(tax);
        return "redirect:/tax";
    }
}
