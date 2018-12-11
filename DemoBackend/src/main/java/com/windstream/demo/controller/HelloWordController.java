package com.windstream.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description just for test
 * @author jinxiaodi
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/hello")
public class HelloWordController {
	
	private static Logger log = LoggerFactory.getLogger(HelloWordController.class);
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		System.out.println("hello world!");
		log.debug("hello world!");
		return "hello";
	}	
}
