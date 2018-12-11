package com.windstream.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.windstream.demo.response.Response;

import io.jsonwebtoken.JwtException;



/**
 * Title: global exception aspect
 * Description: use @ControllerAdvice + @ExceptionHandler
 * process Controller's Exception
 * 
 * @author xiaodi.jin
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAspect {

	private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Response handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		log.error("could_not_read_json...", e);
		return new Response().failure("could_not_read_json");
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Response handleValidationException(MethodArgumentNotValidException e) {
		log.error("parameter_validation_exception...", e);
		return new Response().failure("parameter_validation_exception");
	}

	
	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		log.error("request_method_not_supported...", e);
		return new Response().failure("request_method_not_supported");
	}

	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public Response handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("content_type_not_supported...", e);
		return new Response().failure("content_type_not_supported");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(JwtException.class)
	public Response handleTokenException(Exception e) {
		log.error("Token is invaild...", e);
		return new Response().failure("Token is invaild");
	}
	
	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UsernameNotFoundException.class)
	public Response handleUsernameNotFoundException(Exception e) {
		log.error("User Not Found!", e);
		return new Response().failure("401");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Response handleException(Exception e) {
		log.error("Internal Server Error...", e);
		return new Response().failure("Internal Server Error");
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public Response handleBadCredentialsException(BadCredentialsException e) {
		log.error("Internal Server Error...", e);
		return new Response().failure("Bad credentials");		
	}
	
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	public Response handleAuthenticationException(AuthenticationException e) {
		log.error("AuthenticationException...", e);
		if(e.getMessage().trim()=="Token Expired") {
			return new Response().failure("401");	
		}else {
		return new Response().failure("401");	
		}
	}	
}
