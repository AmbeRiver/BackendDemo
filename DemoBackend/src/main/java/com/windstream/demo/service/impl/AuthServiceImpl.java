package com.windstream.demo.service.impl;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.windstream.demo.beans.Users;
import com.windstream.demo.response.Response;
import com.windstream.demo.service.AuthService;
import com.windstream.demo.service.UsersService;
import com.windstream.demo.util.JwtTokenUtil;

import io.jsonwebtoken.JwtException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UsersService userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil, UsersService userRepository) {
    	
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public Response register(Users userToAdd) {
    	
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        System.out.println("raw pass"+encoder.encode(rawPassword));
        userToAdd.setPassword(encoder.encode(rawPassword));
//        Roles role =new Roles();
//        userToAdd.setRoles((role.setRole("ROLE_USER")));
        userRepository.addUser(userToAdd);
        return new Response().success("add user success");
    }

    @Override
    public HashMap<String,String> login(String username, String password){
    	
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        HashMap<String, String> tokeninfo = new HashMap<>();
        tokeninfo = jwtTokenUtil.generateToken(userDetails);
        return tokeninfo;
    }

    @Override
    public HashMap<String, String> refresh(String oldToken) throws JwtException{
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(username==null) {
        	throw new UsernameNotFoundException("user not found!");
        }else {
        
        Users user = (Users) userDetailsService.loadUserByUsername(username);
        HashMap<String, String> tokeninfo = new HashMap<>();
        
	        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getUpdateDate()==null ? user.getCreateDate():user.getUpdateDate())){
	        	tokeninfo = jwtTokenUtil.refreshToken(token);
	        }else {
	        	new JwtException("Token Expired");
	        	return null;
	        }
            return tokeninfo;
        }
    }

}
