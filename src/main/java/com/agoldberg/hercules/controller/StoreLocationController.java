package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.service.StoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        return new ModelAndView("storelocation/StoreLocationList", "locations", storeLocations);
    }

    @GetMapping("{location_id}")
    public ModelAndView showLocationForm(@PathVariable("location_id") Long id){
        return new ModelAndView("storelocation/StoreLocationForm","location",storeLocationService.getStoreLocationDTO(id));
    }

    @PostMapping("{location_id}")
    public ModelAndView modifyLocation(@PathVariable("location_id") Long id, @Valid @ModelAttribute("location") StoreLocationDTO location, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            storeLocationService.modifyStoreLocation(location);
            return new ModelAndView("redirect:/storelocation");
        }else{
            return new ModelAndView("storelocation/StoreLocationForm","location", location);
        }
    }

    @PostMapping("enable")
    public String toggleEnabled(@ModelAttribute("newLocation") StoreLocationDTO location){
        storeLocationService.toggleStoreLocationEnabled(location.getId());
        return "redirect:/storelocation";
    }

    @PostMapping("create")
    public ModelAndView createStoreLocation(Model model, @Valid @ModelAttribute("newLocation") StoreLocationDTO storeLocation, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()) {
            storeLocationService.createStoreLocation(storeLocation);
            //Add a new StoreLocationDTO to the model to allow us to create store from this page
            model.addAttribute("newLocation", new StoreLocationDTO());
        }else {
            model.addAttribute(storeLocation);
        }
        List<StoreLocationDTO> storeLocations = storeLocationService.getStoreLocations();
        return new ModelAndView("storelocation/StoreLocationList", "locations", storeLocations);
    }

    @PostMapping("delete")
    public String deleteStoreLocation(Model model, @Valid @ModelAttribute("newLocation,") StoreLocationDTO storeLocation, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        storeLocationService.deleteStoreLocation(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return "redirect:/storelocation";
    }

}
