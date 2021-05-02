package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.pojo.Product;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface ProductMapper {
	Product getProductById(Integer id);
	
	void deleteProductById(Integer id);
	
	void updateProduct(Product product);
	
	void insertProduct(Product product);
	
	void mergeProdAndFac(@Param("pid")int pid, @Param("fid")int fid);
	
	void mergeProdAndDev(@Param("pid")int pid, @Param("did")int did);
}
