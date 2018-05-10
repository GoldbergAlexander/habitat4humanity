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
    public ModelAndView createStoreLocation(Model model, @Valid @ModelAttribute("newLocation") StoreLocationDTO storeLocation, BindingResult bindingResult){
        storeLocationService.createStoreLocation(storeLocation);
        return showStoreLocations(model);
    }

    @PostMapping("modify")
    public ModelAndView modifyStoreLocation(Model model, @Valid @ModelAttribute("newLocation") StoreLocationDTO storeLocation, BindingResult bindingResult){
        storeLocationService.modifyStoreLocation(storeLocation);
        return showStoreLocations(model);
    }

    @PostMapping("delete")
    public String deleteStoreLocation(StoreLocationDTO storeLocation){
        storeLocationService.deleteStoreLocation(storeLocation);
        return "redirect:/storelocation/";
    }

}
