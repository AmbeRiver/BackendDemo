package com.windstream.demo.mapper;

import java.util.List;

import com.windstream.demo.beans.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

	Users getUserNamePassword(Users user);
	
	List<Users> getUserList();
}