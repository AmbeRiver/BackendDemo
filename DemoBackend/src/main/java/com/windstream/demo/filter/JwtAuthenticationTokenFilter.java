package com.windstream.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.windstream.demo.response.Response;
import com.windstream.demo.util.JwtTokenUtil;

import io.jsonwebtoken.JwtException;

/**
 * @description this class is a JWT filter class to verify token
 * @author jinxiaodi
 *
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
        
    	// add option process
    	 if (request.getMethod().equals("OPTIONS")){
             response.setHeader("Access-Control-Allow-Origin", "*");
             response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
             response.setHeader("Access-Control-Max-Age", "3600");
             response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
             return;
         }else {
    	// get the request token from request
    	String authHeader = request.getHeader(this.tokenHeader);
    	
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
        	
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            	
                 UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                 
                	if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                		
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        logger.info("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else {
                    	new JwtException("invalidate token");
                    }
            }else {
            	new JwtException("invalidate token");
            }
        }
        chain.doFilter(request, response);
       }
    }
}

