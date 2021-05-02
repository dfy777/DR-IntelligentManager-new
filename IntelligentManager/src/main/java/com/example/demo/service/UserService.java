package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;

public interface UserService {
	
	//check user login
	Result<String> checkUserLogin(String name, String psw, HttpServletRequest request, HttpServletResponse response);
	
	//save new user
	Result<String> saveNewUser(User user, HttpServletRequest request, HttpServletResponse response);
	
	//change user info
	Result<String> changeUserInfo(User new_user);
	
	User findUser(String name);
	
	User findUser(Integer id);
}
