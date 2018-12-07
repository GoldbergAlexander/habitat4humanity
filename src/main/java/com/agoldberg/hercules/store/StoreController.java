package com.agoldberg.hercules.store;

import com.agoldberg.hercules.goal.GoalDTO;
import com.agoldberg.hercules.goal.GoalService;
import com.agoldberg.hercules.size.SizeDTO;
import com.agoldberg.hercules.size.SizeService;
import com.agoldberg.hercules.tax.TaxDTO;
import com.agoldberg.hercules.tax.TaxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/store")
public class StoreController {

    private static final String LOCATIONS_MODEL = "locations";
    private static final String LOCATION_MODEL = "location";
    private static final String LOCATION_REDIRECT = "redirect:/store";
    private static final String NEW_LOCATION_MODEL = "newLocation";
    private static final String LOCATION_LIST_VIEW = "storelocation/StoreList";
    private static final String LOCATION_FORM_VIEW = "storelocation/ModifyStoreForm";

    @Autowired
    private StoreService storeLocationService;

    @Autowired
    private TaxService taxService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping
    public ModelAndView showStores(Model model){
        //Add a new StoreDTO to the model to allow us to create store from this page
        model.addAttribute(NEW_LOCATION_MODEL, new StoreDTO());

        List<StoreDTO> storeLocations = storeLocationService.getStores();
        return new ModelAndView(LOCATION_LIST_VIEW, LOCATIONS_MODEL, storeLocations);
    }

    @GetMapping("{location_id}/tax")
    public ModelAndView showTaxesForStore(Model model, @PathVariable("location_id") Long id){
        model.addAttribute("store", storeLocationService.getStoreDTO(id));
        List<TaxDTO> taxes = taxService.getTaxes(id);
        Map<String, List<TaxDTO>> map = new HashMap<>();
        map.put("data", taxes);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("tax/TaxList", "taxes",taxes);
    }

    @GetMapping("{location_id}/goal")
    public ModelAndView showGoalsForStore(Model model, @PathVariable("location_id") Long id){
        model.addAttribute("store", storeLocationService.getStoreDTO(id));
        List<GoalDTO> goals = goalService.getGoales(id);
        Map<String, List<GoalDTO>> map = new HashMap<>();
        map.put("data", goals);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("goal/GoalList", "goals",goals);
    }

    @GetMapping("{location_id}/size")
    public ModelAndView showSizesForStore(Model model, @PathVariable("location_id") Long id){
        model.addAttribute("store", storeLocationService.getStoreDTO(id));
        List<SizeDTO> sizes = sizeService.getSizesForStore(id);
        Map<String, List<SizeDTO>> map = new HashMap<>();
        map.put("data", sizes);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("size/SizeList", "sizes",sizes);
    }

    @GetMapping("{location_id}")
    public ModelAndView showModifyLocationForm(@PathVariable("location_id") Long id){

            return new ModelAndView(LOCATION_FORM_VIEW, LOCATION_MODEL, storeLocationService.getStore(id));

    }

    @PostMapping("{location_id}")
    public ModelAndView modifyLocation(@PathVariable("location_id") Long id, @Valid @ModelAttribute(LOCATION_MODEL) StoreDTO location, BindingResult bindingResult){
        if(location.getId() == null || !location.getId().equals(id)){
            bindingResult.reject("Bad ID");
        }

        if(!bindingResult.hasErrors()){
            storeLocationService.modifyStore(location);
            return new ModelAndView(LOCATION_REDIRECT);
        }else{
            return new ModelAndView(LOCATION_FORM_VIEW, LOCATION_MODEL, location);
        }
    }

    @PostMapping("enable")
    public String toggleEnabled(@ModelAttribute(NEW_LOCATION_MODEL) StoreDTO location){
        storeLocationService.toggleStoreEnabled(location.getId());
        return LOCATION_REDIRECT;
    }

    @GetMapping("create")
    public ModelAndView showCreateStoreForm(){
        return new ModelAndView("storelocation/CreateStoreForm", "store", new StoreDTO());
    }

    @PostMapping("create")
    public ModelAndView createStore(Model model, @Valid @ModelAttribute(NEW_LOCATION_MODEL) StoreDTO storeLocation, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            storeLocationService.createStore(storeLocation);
            return new ModelAndView(LOCATION_REDIRECT);
        }else {
            model.addAttribute(storeLocation);
        }
        return new ModelAndView("storelocation/CreateStoreForm","store", storeLocation);
    }

    @PostMapping("delete")
    public String deleteStore(Model model, @ModelAttribute(NEW_LOCATION_MODEL) StoreDTO storeLocation, RedirectAttributes redirectAttributes){
        storeLocationService.deleteStore(storeLocation);
        redirectAttributes.addFlashAttribute(model);
        return LOCATION_REDIRECT;
    }


}
