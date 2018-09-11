package com.ericjeffrey.HelloSpringBoot.controller;

import com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        return "index";
    }
    @RequestMapping(value = "/lastPasted" ,method = RequestMethod.GET)
    public String lastPasted(Model model){
        model.addAttribute("content", HelloSpringBootApplication.content);
        return "lastPasted";
    }
}
