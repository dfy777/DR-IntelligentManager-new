package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.PageRequest;
import com.example.demo.pojo.PageResult;
import com.example.demo.pojo.Result;

public interface OrderService {
	PageResult getOrdersOnPageByFacId(PageRequest pageRequest, HttpServletRequest request);
	
	PageResult getAllOrdersOnPage(PageRequest pageRequest, HttpServletRequest request);
	
	Result<String> addOrder(Order order, HttpServletRequest request);
	
	Result<String> addOrderToFactory(Order order, HttpServletRequest request);
	
	Result<String> changeOrder(HttpServletRequest request);
}
