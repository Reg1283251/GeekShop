package com.liam.shop.service;

import com.liam.shop.pojo.User;

public interface UserService {
	
	public int register(User user);
	
	public int active(String code);
	
	public Boolean findByUsername(String username);

}
