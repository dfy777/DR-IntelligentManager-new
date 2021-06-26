package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.pojo.Device;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.Product;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface OrderMapper {
	
	Order getOrderById(Integer id);
	
	Order getOrderByOrdName(String ord_name);
	
	void updateOrder(Order order);
	
	void deleteOrderById(Integer id);
	
	void deleteOrderByOrdName(String ord_name);
	
	void insertOrder(Order order);
	
	List<Order> getOrderByFacIdOnPage(Integer id);
	
	List<Order> getOrderByFacIdAndProdnameOnPage(@Param("fid")Integer fid, @Param("prodname")String prodname);
	
	List<Order> getAllOrders(Integer id);
	
	List<Order> getAllOrdersByProdname(@Param("cid")Integer cid, @Param("prodname")String prodname);
	
	Integer getProdIdByPordName(String name);
	
	Product getProductByPordName(String name);
	
	List<Device> getDevicesByOrdName(String ord_name);
	
	List<Map<String, Object>> getAllProductNum();
}
