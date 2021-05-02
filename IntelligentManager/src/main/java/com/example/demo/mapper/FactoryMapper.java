package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.pojo.Factory;

@Mapper
public interface FactoryMapper {
	Factory getFactoryById(Integer id);
	
	void updateFactory(Factory factory);
	
	void deleteFactoryById(Integer id);
	
	void insertFactory(Factory factory);
	
	List<Factory> selectFactoryPage(Integer user_id);
	
}
