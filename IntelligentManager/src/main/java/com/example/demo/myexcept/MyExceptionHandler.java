package com.example.demo.myexcept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);
	
	public static void takeLog(Exception e) {
		log.error(e.getMessage());
	}
	
	@ExceptionHandler(DeleteUserException.class)
	public void deleteUserException(Exception e) {
		//log.error(e.getMessage());
		takeLog(e);
	}
}
