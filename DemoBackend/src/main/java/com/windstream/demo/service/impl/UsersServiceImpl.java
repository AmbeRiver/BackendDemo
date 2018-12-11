package com.windstream.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.windstream.demo.beans.Users;
import com.windstream.demo.mapper.UsersMapper;
import com.windstream.demo.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);


	@Autowired
	private UsersMapper userMapper;

	@Override
	public Users checkLogin(Users user) {
		// TODO Auto-generated method stub
		logger.debug("in checkLogin, next call usermapper");
		Users users = userMapper.getUserNamePassword(user);
		return users;
	}

	@Transactional
	@Override
	public void addUser(Users user) {
		// TODO Auto-generated method stub
		logger.debug("in addUser, next call usermapper");
        userMapper.insertSelective(user);
	}

	@Transactional
	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		logger.debug("in updateUser, next call usermapper");
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Transactional
	@Override
	public void deleteUser(int userid) {
		// TODO Auto-generated method stub
		logger.debug("in deleteUser, next call usermapper");
		userMapper.deleteByPrimaryKey(userid);	
	}

	@Override
	public Users getUsers(int id) {
		// TODO Auto-generated method stub
		logger.debug("in getUsers by id, next call usermapper");
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		logger.debug("in findByUsername, next call usermapper");
		Users user =new Users();
		user.setUsername(username);
		Users users = userMapper.getUserNamePassword(user);
		return users;		
	}

	@Override
	public List<Users> getUserList() {
		// TODO Auto-generated method stub
		logger.debug("in getUserList, next call usermapper");
		List<Users> users =new ArrayList<Users>();
		users = userMapper.getUserList();
		return users;
	}


}
