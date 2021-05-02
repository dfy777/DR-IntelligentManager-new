package com.example.demo.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1571787161524096374L;
	
	private Integer user_id;
	private String username;
	private String nickname;
	private String email;
	private String password;
	private String telephone;
	private Integer usertype;
	
	public Integer getUser_id() {
		return user_id;
	}
	
	
	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}
	
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Integer getUsertype() {
		return usertype;
	}
	
	
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", nickname=" + nickname + ", email=" + email
				+ ", password=" + password + ", telephone=" + telephone + ", usertype=" + usertype + "]";
	}

	
	public String getNickname() {
		return nickname;
	}
	

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

	/**
	 * if some properties is null, return true
	 * else retrun false
	 * @return
	 */
	public boolean checkIsNull() {
		boolean flag = false;
		if (username==null || nickname==null || email==null || 
			password==null || usertype==null) {
			flag = true;
		}
		return flag;
	}
	
	
	public void updateInfo(User new_user) {
		if (new_user.getNickname() != null) {
			nickname = new_user.getNickname();
		}
		
		if (new_user.getEmail() != null) {
			email = new_user.getEmail();
		}
		
		if (new_user.getPassword() != null) {
			password = new_user.getPassword();
		}
		
		if (new_user.getUsertype() != null) {
			usertype = new_user.getUsertype();
		}
		
		if (new_user.getTelephone() != null) {
			telephone = new_user.getTelephone();
		}
	}
	
	public boolean validateUsername(String username) {
		return PatternUtil.validateName(username);
	}
	
	public boolean validateUsername() {
		return PatternUtil.validateName(this.username);
	}
	
	public boolean validateNickname(String nickname) {
		return PatternUtil.validateName(nickname);
	}
	
	public boolean validateNickname() {
		return PatternUtil.validateName(this.nickname);
	}
	
	public boolean validateTelephone() {
		return PatternUtil.validateTelephone(this.telephone);
	}
	
	public boolean validateTelephone(String telephone) {
		return PatternUtil.validateTelephone(telephone);
	}
	
	public boolean validateEmail() {
		return PatternUtil.validateEmail(this.email);
	}
	
	public boolean validateEmail(String email) {
		return PatternUtil.validateEmail(email);
	}
	
	public boolean validatePassword() {
		return PatternUtil.validatePassword(this.password);
	}
	
	public boolean validatePassword(String password) {
		return PatternUtil.validatePassword(password);
	}
	
	
	public boolean validateUserData() {
		boolean flag = (validateUsername() && validateNickname() &&
						validateEmail()    && validatePassword() &&
						validateTelephone());
		return flag;
	}
	
}
