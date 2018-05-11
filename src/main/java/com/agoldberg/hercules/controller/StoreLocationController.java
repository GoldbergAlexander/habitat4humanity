package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.StoreLocationDTO;
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
import java.util.List;

@Controller
@RequestMapping("/storelocation")
public class StoreLocationController {

    @Autowired
    private StoreLocationService storeLocationService;

    @RequestMapping
    public ModelAndView showStoreLocations(Model model){
        //Add a new StoreLocationDTO to the model to allow us to create store from this page
        model.addAttribute("newLocation", new StoreLocationDTO());

        List<StoreLocationDTO> storeLocations = storeLocationService.getStoreLocations();
        return new ModelAndView("StoreLocationList", "locations", storeLocations);
    }

    @PostMapping("create")
    public String createStoreLocation(Model model, @Valid @ModelAttribute("newLocation") StoreLocationDTO storeLocation, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        storeLocationService.createStoreLocation(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/storelocation";
    }

    @PostMapping("modify")
    public String modifyStoreLocation(Model model, @Valid @ModelAttribute("newLocation") StoreLocationDTO storeLocation, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        storeLocationService.modifyStoreLocation(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/storelocation";
    }

    @PostMapping("delete")
    public String deleteStoreLocation(Model model, @Valid @ModelAttribute("newLocation,") StoreLocationDTO storeLocation, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        storeLocationService.deleteStoreLocation(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/storelocation";
    }

}
