package com.windstream.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.windstream.demo.beans.Users;
import com.windstream.demo.service.UsersService;

/**
 * 
 * @author jinxiaodi
 * 
 * this is a customer UserDetailService implement Spring security UserDetailsService
 * This method is for the user login 
 * 
 */

public class UserDetailServiceCustomer implements UserDetailsService {


	@Autowired
	private UsersService userService;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user =new Users();
		user.setUsername(username);
		user = userService.checkLogin(user);
        if (user == null) {
            new UsernameNotFoundException("User Not Found");
        }
		return user;
	}

}
