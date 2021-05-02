package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pojo.User;

@Controller
public class RedisUserController {
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@ResponseBody
	@RequestMapping("/redis/user")
	public Object createUser() {
		//User user = new User();
		
		//user.setEmaile("231@ed");
		//user.setId(55);
		//user.setNickname("asdaf");
		//user.setType(3);
		//user.setUsername("vvvvvv");
		
		//redisTemplate.opsForValue().set(user.getId().toString(), user);
		//return redisTemplate.opsForValue().get(user.getId().toString());
		return null;
	}
	
}
