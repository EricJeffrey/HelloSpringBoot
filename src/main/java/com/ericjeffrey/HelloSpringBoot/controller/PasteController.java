package com.ericjeffrey.HelloSpringBoot.controller;

import com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class PasteController {

	@PostMapping(value = "/paste")
	public String paste(@RequestParam(name = "content") @RequestBody String content, HttpServletResponse response){
		HelloSpringBootApplication.content = content;
		try {
			response.sendRedirect("/lastPasted");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "hello paste";
	}
}
