package com.agoldberg.hercules.summary;

import com.agoldberg.hercules.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("summary")
public class SummaryController {
    @Autowired
    private StoreService storeService;

    @RequestMapping
    public ModelAndView showSummary(Model model, @ModelAttribute("search") SearchDTO searchDTO, BindingResult bindingResult){
        return null;
    }
}
