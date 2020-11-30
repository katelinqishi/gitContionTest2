package com.securiyt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping("/")
	public String index() {
		System.out.println("我进来了");
		return "index.html";
	}
}
