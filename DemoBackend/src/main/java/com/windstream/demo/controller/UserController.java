package com.windstream.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.windstream.demo.beans.Users;
import com.windstream.demo.response.Response;
import com.windstream.demo.service.UsersService;

/**
 * Title: User management 
 * Description: add or delete user
 * 
 * @author xiaodi.jin
 * @created 2018年8月2日 
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UsersService userService;
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * @description get users by username
	 * @author xiaodi.jin
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/getuserinfo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response getUserByName(String username,String password) {
		log.debug("in getUserByName");
		Users user = new Users();
		user.setUsername(username);
		Users users = userService.checkLogin(user);
		log.debug("user is :" + users);
		Response res =new Response();
		res.success(users);
		return res;
	}
	
    /**
     * @description just for test
     * @return
     */
	@RequestMapping(value = "/hello", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response hello() {
		log.debug("test debug log");
		Response res =new Response();
		res.success();
		res.success("hello");
		return res;
	}	

	/**
	 * @description get users by id
	 * @author xiaodi.jin
	 * @param id
	 * @return
	 */
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getUserById(@PathVariable("id") int id) {
		log.debug("in getUser");
		Users users = userService.getUsers(id);
		log.debug("search user :" + users);
		Response res =new Response();
		res.success();
		res.success(users);
		return res;
	}
	
	/**
	 * @description add users
	 * @author xiaodi.jin
	 * @param users
	 * @return
	 */
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/adduser", method = RequestMethod.POST)
	public Response addUser(@RequestBody @Valid Users user) {
		System.out.println(user.getUserfullname());
        final String username = user.getUsername();
        if(userService.findByUsername(username)!=null) {
            return new Response().failure("User exist");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        System.out.println("raw pass"+encoder.encode(rawPassword));
        user.setPassword(encoder.encode(rawPassword));
        user.setUpdateDate(new Date());
		userService.addUser(user);
		log.debug("add user :" + user);
		Response res =new Response();
		res.success();
		return res;		
	}

	/**
	 * @description update users
	 * @author xiaodi.jin
	 * @param users json front
	 * @return
	 */
	@RequestMapping(value = "/admin/updateuser", method = RequestMethod.POST)
	public Response updateUser(@RequestBody @Valid Users user) {
		Users u=userService.findByUsername(user.getUsername());
		if(user.getPassword()!=null && user.getPassword()!="") {
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        final String rawPassword = user.getPassword();
	        System.out.println("raw pass"+encoder.encode(rawPassword));
	        user.setPassword(encoder.encode(rawPassword));
		}else {
			user.setPassword(null);
			user.setCreateDate(u.getCreateDate());
			user.setUpdateDate(u.getUpdateDate());
		}
		userService.updateUser(user);
		log.debug("add user :" + user);
		Response res =new Response();
		res.success();
		return res;	
	}
	
	/**
	 * @description update users
	 * @author xiaodi.jin
	 * @param users json front
	 * @return
	 */
	@RequestMapping(value = "/user/changepw", method = RequestMethod.POST)
	public Response changePassword(String username,String oldPassword,int id,String password) {		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        final String rawPassword = user.getPassword();
//        System.out.println("raw pass"+encoder.encode(rawPassword));
		Response res =new Response();
        Users u=userService.findByUsername(username);
        boolean pair=encoder.matches(oldPassword, u.getPassword());
        if(pair) {
        	Users user=new Users();
        	user.setId(id);
        	user.setUsername(username);
            user.setPassword(encoder.encode(password));
    		userService.updateUser(user);
    		res.success();
        }else {
        	res.failure();
        	res.failure("password wrong");
        }
		return res;

	}
	
	/**
	 * @description update users
	 * @author xiaodi.jin
	 * @param id [] array
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteuser", method = RequestMethod.POST, produces= "application/json")
	public Response deleteUser(@RequestBody List<Integer> ids ) {
		
		for(int i=0;i<ids.size();i++) {
			userService.deleteUser(ids.get(i));
			log.debug("deleted user :" + ids.get(i));
		}
		Response res =new Response();
		res.success();
		return res;	
	}	
	
	/**
	 * @description get users list
	 * @author xiaodi.jin
	 * @param 
	 * @return list of user
	 */
	@RequestMapping(value = "/admin/getuserlist", method = RequestMethod.GET, produces = "application/json")
	public Response getUserList() {
		List<Users> users=new ArrayList<Users>();
		users=userService.getUserList();
		Response res =new Response();
		res.success();
		res.success(users);
		return res;
	}	
}
