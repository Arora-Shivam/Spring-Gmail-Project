package com.gmail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@GetMapping(value = "/uml")
	public String getUmlDiagram() {
		return "UML.pdf";
	}

	@RequestMapping(value = "/welcome")
	public String welcome() {
		return "index.html";
	}
}
