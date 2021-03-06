package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.DeviceMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.ResultUtil;

@Service
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	OrderMapper orderMapper;

	@Override
	public Result<List<Integer>> showIndexDeviceInfo() {
		List<Map<String, Object>> deviceList = deviceMapper.getAllProgressAndStatus();
		Integer data[] = new Integer[] {0,0,0,0};
		
		for (Map<String, Object> item : deviceList) {
			switch (item.get("status").toString()) {
			case "闲置中":
				data[0] += 1;
				break;
				
			case "进行中":
				data[1] += 1;
				break;
			
			case "未运行":
				data[2] += 1;
				break;
				
			case "已完成":
				data[3] += 1;
				break;
				
			default:
				break;
			}
		}
		List<Integer> resultList = Arrays.asList(data);
		return ResultUtil.successRes(resultList);
	}
	
	
	@Override
	public Result<List<List<Object>>> showIndexProductInfo() {
		List<Map<String, Object>> orderList = orderMapper.getAllProductNum();
		List<Map<String, Object>> deviceList = deviceMapper.getAllProgressAndProdNum();
		//记录待生产的产品总数
		Map<String, Integer> maxProdNumMap = new HashMap<>();
		//记录已生产的产品总数
		Map<String, Integer> createdNumMap = new HashMap<>();
		
		//先处理生产总数的map
		for (Map<String, Object> item : orderList) {
			Integer tmpInt = Integer.parseInt(item.get("num").toString());
			
			if (maxProdNumMap.containsKey(item.get("name").toString())) {
				tmpInt += maxProdNumMap.get(item.get("name").toString());
			}
			
			maxProdNumMap.put(item.get("name").toString(), tmpInt);
		}
		
		//处理订单已生产产品数的map
		for (Map<String, Object> item : orderList) {
			//先计算出该订单已交付的部分
			Integer tmpInt = Integer.parseInt(item.get("num").toString());
			Double tmpDou = Integer.parseInt(item.get("progress").toString()) / 100D;
			
			tmpDou *= tmpInt;
			tmpInt = tmpDou.intValue();
			
			if (createdNumMap.containsKey(item.get("name").toString())) {
				tmpInt += createdNumMap.get(item.get("name").toString());
			}
			
			createdNumMap.put(item.get("name").toString(), tmpInt);
		}
		
		//处理设备已生产部分
		for (Map<String, Object> item : deviceList) {
			Integer tmpInt = Integer.parseInt(item.get("num").toString());
			Double tmpDou = Integer.parseInt(item.get("progress").toString()) / 100D;
			
			tmpDou *= tmpInt;
			tmpInt = tmpDou.intValue();
			tmpInt += createdNumMap.get(item.get("name").toString());
			
			createdNumMap.put(item.get("name").toString(), tmpInt);
		}
		
		List<List<Object>> resultList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<Integer> maxNumList = new ArrayList<>();
		List<Integer> createdNumList = new ArrayList<>();
		
		for (Map.Entry<String, Integer> entry : maxProdNumMap.entrySet()) {
			nameList.add(entry.getKey());
			maxNumList.add(entry.getValue());
			createdNumList.add(entry.getValue() - createdNumMap.get(entry.getKey()));
		}
		
		List<Object> tmpList1 = new ArrayList<>(nameList);
		List<Object> tmpList2 = new ArrayList<>(maxNumList);
		List<Object> tmpList3 = new ArrayList<>(createdNumList);
		
		resultList.add(tmpList1);
		resultList.add(tmpList2);
		resultList.add(tmpList3);
		
		return ResultUtil.successRes(resultList);
	}
}
