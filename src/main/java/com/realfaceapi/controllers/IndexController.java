package com.realfaceapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	@RequestMapping
	public String inicio() {
		return "index.html";
	}
	

}
