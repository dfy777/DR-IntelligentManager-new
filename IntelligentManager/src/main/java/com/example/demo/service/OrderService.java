package com.example.demo.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.PageRequest;
import com.example.demo.pojo.PageResult;
import com.example.demo.pojo.Result;

public interface OrderService {
	PageResult getOrdersOnPageByFacId(Map<String, String> requestMap, HttpServletRequest request);
	
	PageResult getAllOrdersOnPage(Map<String, String> requestMap, HttpServletRequest request);
	
	Result<String> addOrder(Order order, HttpServletRequest request);
	
	Result<String> deleteOrder(HttpServletRequest request);
	
	Result<String> addOrderToFactory(Order order, HttpServletRequest request);
	
	Result<String> changeOrder(HttpServletRequest request);
	
	Result<String> deliveResultOrder(HttpServletRequest request);
}
