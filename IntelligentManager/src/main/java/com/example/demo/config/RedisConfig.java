package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Configuration
public class RedisConfig {
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate (
			RedisConnectionFactory redisConnectionFactory) {
		
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		
		// 配置连接工厂(处理连接池的)
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		
		//使用json的序列化方式
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = 
				new Jackson2JsonRedisSerializer<Object>(Object.class);
		
		//设置序列化的一些配置
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		//设置序列化可见性
		//指定要序列化的域，field get和set以及修饰符范围
		//ANY指包括private和public
		objectMapper.setVisibility(PropertyAccessor.ALL,
				JsonAutoDetect.Visibility.ANY);
		
		//指定序列化输入的类型，用final修饰的类不序列化
		//objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		
		//装填配置
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		
		//值采用json序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		
		//key使用stringredis序列器来序列化
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
		//设置hash key和value的序列化模式
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		
		//设置完成
		return redisTemplate;
	}
}
