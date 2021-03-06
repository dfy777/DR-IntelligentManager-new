package com.example.demo.pojo;

import java.io.Serializable;

public class Factory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3992330748822864864L;

	private Integer factory_id;
	private String fac_name;
	private Integer client_id;
	private String fac_location;
	private String username;
	private String phone;
	
	public Integer getFactory_id() {
		return factory_id;
	}
	
	public void setFactory_id(Integer factory_id) {
		this.factory_id = factory_id;
	}
	
	public void setFactoryId(Integer factory_id) {
		this.factory_id = factory_id;
	}
	
	public String getFac_name() {
		return fac_name;
	}
	
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	
	public void setFacName(String fac_name) {
		this.fac_name = fac_name;
	}
	
	public Integer getClient_id() {
		return client_id;
	}
	
	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}
	
	public void setClientId(Integer client_id) {
		this.client_id = client_id;
	}
	
	public String getFac_location() {
		return fac_location;
	}
	
	public void setFac_location(String fac_location) {
		this.fac_location = fac_location;
	}
	
	public void setFacLocation(String fac_location) {
		this.fac_location = fac_location;
	}

	
	public boolean validateFac_name() {
		return PatternUtil.validateName(this.fac_name);
	}
	
	public boolean validateFac_name(String name) {
		return PatternUtil.validateName(name);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Factory [factory_id=" + factory_id + ", fac_name=" + fac_name + ", client_id=" + client_id
				+ ", fac_location=" + fac_location + ", username=" + username + ", phone=" + phone + "]";
	}
}
