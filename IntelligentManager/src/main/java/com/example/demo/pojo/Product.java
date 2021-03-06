package com.example.demo.pojo;

import java.io.Serializable;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4751471923038152939L;
	
	private Integer product_id;
	private Integer price;
	private String prod_name;
	private Integer num;
	
	public Integer getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	
	public void setProductId(Integer product_id) {
		this.product_id = product_id;
	}
	
	
	public String getProd_name() {
		return prod_name;
	}
	
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	
	public void setProdName(String prod_name) {
		this.prod_name = prod_name;
	}

	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", price=" + price + ", prod_name=" + prod_name + "]";
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
