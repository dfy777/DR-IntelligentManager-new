package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pojo.Result;
import com.example.demo.service.IndexService;


@Controller
public class HelloController {
	
	@Autowired
	IndexService indexService;
	
	@GetMapping("/home")
	public String index() {
		return "index";
	}
	
	@GetMapping("/home/index/show/device-info")
	@ResponseBody
	public Result<List<Integer>> showDeviceInfo() {
		return indexService.showIndexDeviceInfo();
	}
	
	@GetMapping("/home/index/show/product-info")
	@ResponseBody
	public Result<List<List<Object>>> testIndex() {
		return indexService.showIndexProductInfo();
	}
}
