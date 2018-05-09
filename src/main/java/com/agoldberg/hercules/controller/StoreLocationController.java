package com.agoldberg.hercules.controller;

import com.agoldberg.hercules.dto.StoreLocationDTO;
import com.agoldberg.hercules.service.StoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String createStoreLocation(StoreLocationDTO storeLocation){
        storeLocationService.createStoreLocation(storeLocation);
        return "redirect:/storelocation/";
    }

    @PostMapping("modify")
    public String modifyStoreLocation(StoreLocationDTO storeLocation){
        storeLocationService.modifyStoreLocation(storeLocation);
        return "redirect:/storelocation/";
    }

    @PostMapping("delete")
    public String deleteStoreLocation(StoreLocationDTO storeLocation){
        storeLocationService.deleteStoreLocation(storeLocation);
        return "redirect:/storelocation/";
    }

}
