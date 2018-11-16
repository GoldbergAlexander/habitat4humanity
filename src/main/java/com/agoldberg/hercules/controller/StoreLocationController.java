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

    private static final String LOCATIONS_MODEL = "locations";
    private static final String LOCATION_MODEL = "location";
    private static final String LOCATION_REDIRECT = "redirect:/storelocation";
    private static final String NEW_LOCATION_MODEL = "newLocation";
    private static final String LOCATION_LIST_VIEW = "storelocation/StoreLocationList";
    private static final String LOCATION_FORM_VIEW = "storelocation/StoreLocationForm";

    @Autowired
    private StoreLocationService storeLocationService;

    @RequestMapping
    public ModelAndView showStoreLocations(Model model){
        //Add a new StoreLocationDTO to the model to allow us to create store from this page
        model.addAttribute(NEW_LOCATION_MODEL, new StoreLocationDTO());

        List<StoreLocationDTO> storeLocations = storeLocationService.getStoreLocations();
        return new ModelAndView(LOCATION_LIST_VIEW, LOCATIONS_MODEL, storeLocations);
    }

    @GetMapping("{location_id}")
    public ModelAndView showLocationForm(@PathVariable("location_id") Long id){
        return new ModelAndView(LOCATION_FORM_VIEW, LOCATION_MODEL,storeLocationService.getStoreLocationDTO(id));
    }

    @PostMapping("{location_id}")
    public ModelAndView modifyLocation(@PathVariable("location_id") Long id, @Valid @ModelAttribute(LOCATION_MODEL) StoreLocationDTO location, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            storeLocationService.modifyStoreLocation(location);
            return new ModelAndView(LOCATION_REDIRECT);
        }else{
            return new ModelAndView(LOCATION_FORM_VIEW, LOCATION_MODEL, location);
        }
    }

    @PostMapping("enable")
    public String toggleEnabled(@ModelAttribute(NEW_LOCATION_MODEL) StoreLocationDTO location){
        storeLocationService.toggleStoreLocationEnabled(location.getId());
        return LOCATION_REDIRECT;
    }

    @PostMapping("create")
    public ModelAndView createStoreLocation(Model model, @Valid @ModelAttribute(NEW_LOCATION_MODEL) StoreLocationDTO storeLocation, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            storeLocationService.createStoreLocation(storeLocation);
            //Add a new StoreLocationDTO to the model to allow us to create store from this page
            model.addAttribute(NEW_LOCATION_MODEL, new StoreLocationDTO());
        }else {
            model.addAttribute(storeLocation);
        }
        List<StoreLocationDTO> storeLocations = storeLocationService.getStoreLocations();
        return new ModelAndView(LOCATION_LIST_VIEW, LOCATIONS_MODEL, storeLocations);
    }

    @PostMapping("delete")
    public String deleteStoreLocation(Model model, @Valid @ModelAttribute(NEW_LOCATION_MODEL) StoreLocationDTO storeLocation, RedirectAttributes redirectAttributes){
        storeLocationService.deleteStoreLocation(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return LOCATION_REDIRECT;
    }

}
