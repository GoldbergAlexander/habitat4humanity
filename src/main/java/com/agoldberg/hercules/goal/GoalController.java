package com.agoldberg.hercules.goal;

import com.agoldberg.hercules.store.StoreService;
import com.agoldberg.hercules.tax.TaxDTO;
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
@RequestMapping("/goal")
public class GoalController {
    @Autowired
    private GoalService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ModelAndView showGoales(Model model){
        model.addAttribute("newGoal", new GoalDTO());
        List<GoalDTO> goals = service.getGoales();

        Map<String, List<GoalDTO>> map = new HashMap<>();
        map.put("data", goals);
        try {
            String js = objectMapper.writeValueAsString(map);
            js = '[' + js +']';
            model.addAttribute("js",js);
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        return new ModelAndView("goal/GoalList","goals",goals);
    }

    @GetMapping("create")
    public ModelAndView showCreateForm(Model model){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("goal/CreateGoalForm","goal", new GoalDTO());
    }

    @PostMapping("create")
    public ModelAndView createGoal(Model model, @Valid @ModelAttribute("goal") GoalDTO goal, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.createGoal(goal);
            return new ModelAndView("redirect:/goal");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute(goal);
        }
        return new ModelAndView("goal/CreateGoalForm","store", goal);
    }

    @GetMapping("{goal_id}")
    public ModelAndView showModifyForm(Model model,@PathVariable("goal_id") Long id){
        model.addAttribute("stores", storeService.getEnabledStores());
        return new ModelAndView("goal/ModifyGoalForm","goal",service.getGoal(id));
    }

    @PostMapping("{goal_id}")
    public ModelAndView modifyGoal(Model model, @PathVariable("goal_id") Long id, @Valid @ModelAttribute("goal") GoalDTO goal, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.modifyGoal(goal);
            return new ModelAndView("redirect:/goal");
        }else {
            model.addAttribute("stores", storeService.getEnabledStores());
            model.addAttribute(goal);
        }
        return new ModelAndView("goal/ModifyGoalForm","goal",service.getGoal(id));
    }

    @PostMapping("delete")
    public String deleteStore(Model model, @ModelAttribute("newGoal") GoalDTO goal){
        service.deleteGoal(goal);
        return "redirect:/goal";
    }
}
