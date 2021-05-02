package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {
	
	@GetMapping("/home")
	public String index() {
		return "index";
	}
	
	@GetMapping("/home/user/test")
	@ResponseBody
	public String testIndex() {
		return "ok1";
	}
	
	@GetMapping("/notpage")
	@ResponseBody
	public String testIndex2() {
		return "ok2";
	}
	
	@GetMapping("/notpage/test")
	@ResponseBody
	public String testIndex3() {
		return "ok3";
	}
	
}
