package com.example.demo.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5994604583240811380L;
	private Integer order_id;
	private Integer fac_id;
	private Integer client_id;
	private Integer prodplan_id;
	private Integer dev_id;
	private Integer prod_id;
	private Integer prod_num;
	private Integer progress;
	private String ord_name;
	private String prod_name;
	private String description;
	private String pay_status;
	private String pay_func;
	private String status;
	private Integer price;
	private String receiver;
	private String phone;
	private String location;
	
	public Integer getOrder_id() {
		return order_id;
	}
	
	
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
	
	public void setOrderId(Integer order_id) {
		this.order_id = order_id;
	}
	
	
	public Integer getFac_id() {
		return fac_id;
	}
	
	
	public void setFac_id(Integer fac_id) {
		this.fac_id = fac_id;
	}
	
	
	public void setFacId(Integer fac_id) {
		this.fac_id = fac_id;
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
	
	
	public Integer getProdplan_id() {
		return prodplan_id;
	}
	
	
	public void setProdplan_id(Integer prodplan_id) {
		this.prodplan_id = prodplan_id;
	}
	
	
	public void setProdplanId(Integer prodplan_id) {
		this.prodplan_id = prodplan_id;
	}
	
	
	public Integer getDev_id() {
		return dev_id;
	}
	
	
	public void setDev_id(Integer dev_id) {
		this.dev_id = dev_id;
	}
	
	
	public void setDevId(Integer dev_id) {
		this.dev_id = dev_id;
	}
	
	
	public String getOrd_name() {
		return ord_name;
	}
	
	
	public void setOrd_name(String ord_name) {
		this.ord_name = ord_name;
	}
	
	
	public void setOrdName(String ord_name) {
		this.ord_name = ord_name;
	}
	
	
	public String getDescription() {
		return description;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getPay_status() {
		return pay_status;
	}
	
	
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	
	
	public void setPayStatus(String pay_status) {
		this.pay_status = pay_status;
	}
	
	
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Integer getPrice() {
		return price;
	}
	
	
	public void setPrice(Integer price) {
		this.price = price;
	}

	
	/**
	 * ????????????????????????????????????false,????????????true
	 * @return
	 */
	public boolean generateOrd_name() {
		if (this.ord_name == null) {
			
			//??????17???????????????20170411094039080
			SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String strDate = sfDate.format(new Date());
			
			//??????4????????????
			String rdstr="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			StringBuilder sb=new StringBuilder(4);
			
			for(int i=0;i<4;i++) {
				//public int nextInt(int n) ??????????????????????????????????????????int??????????????????[0,n)?????????????????????0???n???????????????int????????????0????????????n???
				char ch = rdstr.charAt(new Random().nextInt(rdstr.length()));
				sb.append(ch);
			}
			
			this.ord_name = strDate + sb.toString();
			
			return true;
		}
		else {
			return false;
		}
	}


	public Integer getProd_num() {
		return prod_num;
	}


	public void setProd_num(Integer prod_num) {
		this.prod_num = prod_num;
	}
	
	public void setProdNum(Integer prod_num) {
		this.prod_num = prod_num;
	}


	public String getPay_func() {
		return pay_func;
	}


	public void setPay_func(String pay_func) {
		this.pay_func = pay_func;
	}
	
	
	public void setPayFunc(String pay_func) {
		this.pay_func = pay_func;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", fac_id=" + fac_id + ", client_id=" + client_id + ", prodplan_id="
				+ prodplan_id + ", dev_id=" + dev_id + ", prod_id=" + prod_id + ", prod_num=" + prod_num + ", ord_name="
				+ ord_name + ", description=" + description + ", pay_status=" + pay_status + ", pay_func=" + pay_func
				+ ", status=" + status + ", price=" + price + ", receiver=" + receiver + ", phone=" + phone
				+ ", location=" + location + "]";
	}


	public Integer getProd_id() {
		return prod_id;
	}


	public void setProd_id(Integer prod_id) {
		this.prod_id = prod_id;
	}
	
	
	public void setProdId(Integer prod_id) {
		this.prod_id = prod_id;
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


	public Integer getProgress() {
		return progress;
	}


	public void setProgress(Integer progress) {
		this.progress = progress;
	}
}
