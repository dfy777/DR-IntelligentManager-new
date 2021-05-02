package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.service.RedisSessionInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	
	//必须要重写这个方法，不然无法调用redistemplate
	@Bean
	public RedisSessionInterceptor getSessionInterceptor() {
		return new RedisSessionInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getSessionInterceptor())
				.addPathPatterns("/home/**")
				.excludePathPatterns("/home/user/**");
		super.addInterceptors(registry);
	}
}
