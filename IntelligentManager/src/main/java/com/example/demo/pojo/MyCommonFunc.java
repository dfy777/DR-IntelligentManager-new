package com.example.demo.pojo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MyCommonFunc {
	public static String getRandomString(int length) {
		String str="abcdefghijklmnopqrstuvwxyz";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 0; i < 6 ; i++){
	    	int number = random.nextInt(26);
	    	sb.append(str.charAt(number));
	    }
	    
	    return sb.toString();
	}
	
	public static String getDelayDateOnSecond(int second) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);	
		date = calendar.getTime();
		
		return ft.format(date);
	}
}
