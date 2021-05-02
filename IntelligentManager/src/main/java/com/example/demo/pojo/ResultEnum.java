package com.example.demo.pojo;

public enum ResultEnum {
	UNKNOWN_ERROR(10000, "unknow error"),
	SUCCESS(20000, "success"),
	CUSTOM_MSG(20001,"customize"),
	DATA_NOT_FOUND(30005, "data_not_found"),
	DATA_NOT_ALLOWED(30006, "data_not_allowed"),
	DATA_IS_NULL(30010,"data is null"),
	INVALID_DATA(30009,"invalid_data")
	;
	
	private Integer code;
	private String msg;
	
	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public String getMsg() {
		return this.msg;
	}
}
