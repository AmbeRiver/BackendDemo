package com.windstream.demo.util;

import java.util.UUID;

  
/**        
 * Title: 生成UUID    
 * @author xiaodi.jin       
 */      
public class CodecUtil {
	
	public static String createUUID(){
		return UUID.randomUUID().toString();
	}
}
