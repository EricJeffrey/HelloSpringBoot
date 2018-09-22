package com.ericjeffrey.HelloSpringBoot.controller;

import com.ericjeffrey.HelloSpringBoot.utils.DataGrocery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ericjeffrey.HelloSpringBoot.utils.DataGrocery.VISIT_INC;

@RestController
public class PasteController {

	@PostMapping(value = "/paste")
	public String paste(@RequestParam(name = "content") @RequestBody String content, HttpServletResponse response){
	    if (content != null && content.length() > 0)
		    DataGrocery.add(content);
		try {
			response.sendRedirect("/lastPasted");
			VISIT_INC();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "hello paste";
	}
}
