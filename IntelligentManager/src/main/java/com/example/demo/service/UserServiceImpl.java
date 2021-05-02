package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.myexcept.DeleteUserException;
import com.example.demo.myexcept.MyExceptionHandler;
import com.example.demo.pojo.CookieUtil;
import com.example.demo.pojo.DValueEnum;
import com.example.demo.pojo.PatternUtil;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.ResultUtil;
import com.example.demo.pojo.User;

import io.netty.util.internal.StringUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	
	/**
	 * save user
	 */
	private void insertUser(User user) {
		userMapper.insertUser(user);
	}
	
	/**
	 * delete user
	 */
	private boolean deleteUser(Integer id) throws DeleteUserException {
		try {
			int res = userMapper.deleteUserById(id);
			if (res > 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			throw new DeleteUserException();
		}
	}
	
	private boolean deleteUser(User user) throws DeleteUserException {
		try {
			return deleteUser(user.getUser_id());
		} catch (Exception e) {
			throw new DeleteUserException();
		}
	}
	
	private boolean deleteUser(String name) throws DeleteUserException {
		try {
			User user = userMapper.getUserByName(name);
			return deleteUser(user.getUser_id());
		} catch (Exception e) {
			throw new DeleteUserException();
		}
	}
	
	
	/**
	 * find User
	 */
	
	@Override
	public User findUser(Integer id) {
		return userMapper.getUserById(id);
	}
	
	
	@Override
	public User findUser(String name) {
		return userMapper.getUserByName(name);
	}
	
	
	/**
	 * user log in
	 */
	@Override
	public Result<String> checkUserLogin(String name, String psw, 
							HttpServletRequest request, HttpServletResponse response) {
		
		//检测是否为空值
		if (StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(psw)) {
			return ResultUtil.null_dataRes("输入用户名或密码为空");
		}
		
		//检测用户名和密码格式是否合法
		if ( !(PatternUtil.validateName(name) && PatternUtil.validatePassword(psw)) ) {
			return ResultUtil.data_not_allowedRes("非法字符串");
		}
		
		User user =  userMapper.getUserByName(name);
		
		if (user.checkIsNull()) {
			return ResultUtil.data_not_foundRes("未找到用户");
		} else if (user.getPassword().equals(psw)) {
			
			//设置session
			HttpSession session = request.getSession();	
			session.setAttribute(DValueEnum.LOGIN_USER_ID.getValue(), user.getUser_id());
			
			//设置cookie,值为用户id
			CookieUtil.addCookie(response, DValueEnum.COOKIE_USER_ID.getValue(), 
									user.getUser_id().toString(), 24 * 60 * 60);
			
			//存储session
			redisTemplate.opsForValue().set(DValueEnum.LOGIN_USER.getValue() + user.getUser_id(), 
												session.getId());
			
			return ResultUtil.successRes("登陆成功");
		} else {
			return ResultUtil.unknown_errorReult("密码错误");
		}
	}
	
	/**
	 * user log up
	 */
	@Override
	public Result<String> saveNewUser(User user, HttpServletRequest request, HttpServletResponse response) {
		try {
			user.setUsertype(5);
			//check if user properties is null
			if (user.checkIsNull()) {
				return ResultUtil.null_dataRes("用户数据为空");
			}
			
			if (!user.validateUserData()) {
				return ResultUtil.data_not_allowedRes("非法数据");
			}
			
			int insert_row = userMapper.insertUser(user);
			if (insert_row > 0) {
				
				//设置session
				HttpSession session = request.getSession();	
				session.setAttribute(DValueEnum.LOGIN_USER_ID.getValue(), user.getUser_id());
				
				//设置cookie,值为用户id
				CookieUtil.addCookie(response, DValueEnum.COOKIE_USER_ID.getValue(), 
										user.getUser_id().toString(), 24 * 60 * 60);
				
				//存储session
				redisTemplate.opsForValue().set(DValueEnum.LOGIN_USER.getValue() + user.getUser_id(), 
													session.getId());
				
				return ResultUtil.successRes("登陆成功");
			} else {
				return ResultUtil.unknown_errorReult("保存用户错误");
			}
			
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return ResultUtil.unknown_errorReult("保存用户错误");
		}
	}
	
	
	
	
	@Override
	public Result<String> changeUserInfo(User new_user) {
		try {
			if (new_user.checkIsNull()) {
				return ResultUtil.null_dataRes("数据不能为空");
			}
			
			if (!new_user.validateUserData()) {
				return ResultUtil.data_not_allowedRes("非法数据");
			}
			
			User old_user = findUser(new_user.getUsername());
			if (old_user == null) {
				return ResultUtil.null_dataRes("找不到用户");
			}
			
			old_user.updateInfo(new_user);
			
			int update_row = userMapper.updateUser(old_user);
			if (update_row > 0) {
				return ResultUtil.successRes("修改成功");
			} else {
				return ResultUtil.unknown_errorReult("修改失败");
			}
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return ResultUtil.unknown_errorReult("修改失败");
		}
	}
}
