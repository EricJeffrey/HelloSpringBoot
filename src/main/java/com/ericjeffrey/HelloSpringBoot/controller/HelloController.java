package com.ericjeffrey.HelloSpringBoot.controller;

import com.ericjeffrey.HelloSpringBoot.utils.DataGrocery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.ericjeffrey.HelloSpringBoot.utils.DataGrocery.VISIT_INC;
import static com.ericjeffrey.HelloSpringBoot.utils.DataGrocery.getVisitCount;

@Controller
public class HelloController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Model model) {
        return index(model);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        VISIT_INC();
        model.addAttribute("pv", getVisitCount());
        return "index";
    }

    @RequestMapping(value = "/lastPasted", method = RequestMethod.GET)
    public String lastPasted(Model model) {
        model.addAttribute("date", DataGrocery.topDate());
        model.addAttribute("content", DataGrocery.topContent());
        return "lastPasted";
    }

    @RequestMapping(value = "/pasted", method = RequestMethod.GET)
    public String pasted(@RequestParam(name = "i") @RequestBody String index, Model model) {
        model.addAttribute("date", DataGrocery.dateOfI(Integer.parseInt(index)));
        model.addAttribute("content", DataGrocery.contentOfI(Integer.parseInt(index)));
        return "lastPasted";
    }
}
