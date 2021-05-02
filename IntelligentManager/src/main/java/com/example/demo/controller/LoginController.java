package com.example.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;


@Controller
@RequestMapping("/home/user")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/sign-up")
	public String getSignUpPage() {
		return "admin-add";
	}
	
	
	@GetMapping("/sign-in")
	public String getSignInPage() {
		return "login";
	}
	
	
	@GetMapping(value="/sign-in/login",consumes={"application/json"})
	@ResponseBody
	public Result<String> checkUserLogin(HttpServletRequest request, HttpServletResponse response) {
		//String username = userPara.get("username");
		//String psw = userPara.get("password");
		
		String username = request.getParameter("username");
		String psw = request.getParameter("password");
		
		
		return  userService.checkUserLogin(username, psw, request, response);
	}
	
	
	@PostMapping(value="/sign-up/logup",consumes={"application/json"}) 
	@ResponseBody 
	public Result<String> saveNewUser(@RequestBody User user,
									HttpServletRequest request, HttpServletResponse response) {
		return userService.saveNewUser(user, request, response);
	}
}
