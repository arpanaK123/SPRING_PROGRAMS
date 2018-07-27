package com.bridgeit.javaConfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreetController {
	@RequestMapping(value = "/greet/{name}", method = RequestMethod.GET)
	public String greet(@PathVariable String name, ModelMap model) {
		String greet = " Hello  " + name + " bridgelabz?";
		model.addAttribute("greet", greet);
		System.out.println(greet);
		return "greet";
	}

}
