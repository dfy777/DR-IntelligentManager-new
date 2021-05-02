package com.example.demo.pojo;

public class ResultUtil {
	public static <T> Result<T> successRes(T object) {
		
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.SUCCESS.getCode());
		result.setMsg(ResultEnum.SUCCESS.getMsg());
		result.setData(object);
		
		return result;
	}
	
	
	public static <T> Result<T> successRes() {
		return successRes(null);
	}
	
	
	public static <T> Result<T> unknown_errorReult(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.UNKNOWN_ERROR.getCode());
		result.setMsg(ResultEnum.UNKNOWN_ERROR.getMsg());
		result.setData(object);
		
		return result;
	}
	
	
	public static <T> Result<T> data_not_foundRes(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.DATA_NOT_FOUND.getCode());
		result.setMsg(ResultEnum.DATA_NOT_FOUND.getMsg());
		result.setData(object);
		
		return result;
	}
	
	
	public static <T> Result<T> null_dataRes(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.DATA_IS_NULL.getCode());
		result.setMsg(ResultEnum.DATA_IS_NULL.getMsg());
		result.setData(object);
		
		return result;
	}
	
	
	public static <T> Result<T> custom_msgRes(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.CUSTOM_MSG.getCode());
		result.setMsg(ResultEnum.CUSTOM_MSG.getMsg());
		result.setData(object);
		
		return result;
	}
	
	public static <T> Result<T> data_not_allowedRes(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.DATA_NOT_ALLOWED.getCode());
		result.setMsg(ResultEnum.DATA_NOT_ALLOWED.getMsg());
		result.setData(object);
		
		return result;
	}
	
	public static <T> Result<T> invalid_data(T object) {
		Result<T> result = new Result<T>();
		result.setCode(ResultEnum.INVALID_DATA.getCode());
		result.setMsg(ResultEnum.INVALID_DATA.getMsg());
		result.setData(object);
		
		return result;
	}
}
