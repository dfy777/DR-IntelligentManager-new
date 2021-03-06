package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pojo.Factory;
import com.example.demo.pojo.PageRequest;
import com.example.demo.pojo.PageResult;
import com.example.demo.pojo.Result;
import com.example.demo.service.FactoryService;


@Controller
public class FactoryController {
	
	@Autowired
	FactoryService factoryService;
	
	
	@GetMapping("/home/factory/show")
	public String factoryShowPage(HttpServletResponse response) {
		return "factory-list";
	}
	
	/**
	 * 记录当前工厂id
	 * @param request
	 * @return
	 */
	@PostMapping("/home/factory/show/select-factory")
	@ResponseBody
	public Result<String> selectedFactorySession(HttpServletRequest request) {
		return factoryService.selectedFactorySession(request);
	}
	
	
	/**
	 * 工厂分页显示
	 * @param pageRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/home/factory/show/select_all")
	@ResponseBody
	public PageResult getAllFactoryByPage(@RequestBody PageRequest pageRequest, HttpServletRequest request) {
		//System.out.println("pageok");
		return factoryService.getAllFactorisByPage(pageRequest, request);
	}
	
	
	
	@PostMapping("/home/factory/add-factory")
	@ResponseBody
	public Result<String> addFactory(@RequestBody Factory factory, HttpServletRequest request) {
		return factoryService.addFactory(request, factory);
	}
}
