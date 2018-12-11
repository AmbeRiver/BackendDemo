package com.windstream.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.windstream.demo.beans.Users;
import com.windstream.demo.response.JwtAuthenticationResponse;
import com.windstream.demo.response.Response;
import com.windstream.demo.service.AuthService;

import io.jsonwebtoken.JwtException;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @description this class is auth controller, we use it to return token when use login/refresh
 * @author jinxiaodi
 *
 */
@CrossOrigin
@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;
    
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken( String username,String password) throws AuthenticationException,BadCredentialsException{
    	
        final HashMap<String, String> token = authService.login(username,password);
        Response res =new Response();
        res.success();
        res.success(new JwtAuthenticationResponse(token.get("Token"),token.get("ExpireDate")));
        // Return the token
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws AuthenticationException{
    	
        String token = request.getHeader(tokenHeader);
        HashMap<String, String> refreshedToken = authService.refresh(token);

        Response res =new Response();
        res.success();
        res.success(new JwtAuthenticationResponse(refreshedToken.get("Token"),refreshedToken.get("ExpireDate")));
        
        if(refreshedToken.get("Token")==null) {
            new JwtException("Token Expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(res);
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public Response register(String username,String password,String role) throws AuthenticationException{
    	Users addedUser = new Users();
//    	addedUser.setDepartment("");
    	addedUser.setPassword(password);
    	addedUser.setUsername(username);
    	addedUser.setRole(role);
    	logger.debug("register user: "+addedUser.getUsername());
        return authService.register(addedUser);
    }
}
