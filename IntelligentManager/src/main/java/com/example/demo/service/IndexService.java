package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.pojo.Result;

public interface IndexService {
	/**
	 * 返回主页中展示设备总体数据的方法
	 * @return
	 */
	Result<List<Integer>> showIndexDeviceInfo();
	
	
	/**
	 * 返回主页中展示设备总体产品的方法
	 * @return
	 */
	Result<List<List<Object>>> showIndexProductInfo();
}
