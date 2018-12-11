package com.windstream.demo.service;

import java.util.List;

import com.windstream.demo.beans.Users;

public interface UsersService {
	
	public Users getUsers(int id);

    public Users checkLogin(Users user);
    
    public void addUser(Users user);
    
    public void updateUser(Users user);
    
    public void deleteUser(int userid);

	public Users findByUsername(String username);
	
	public List<Users> getUserList();

}
