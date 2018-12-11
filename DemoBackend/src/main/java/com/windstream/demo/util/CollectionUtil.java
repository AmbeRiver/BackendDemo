package com.windstream.demo.util;

import java.util.Collection;

  
/**        
 * Title: Collection 工具类    
 * Description: 
 * @author xiaodi.jin       
 */      
public class CollectionUtil {
	public static boolean isNotEmpty(Collection<?> c){
		if (c != null && c.size() != 0 ) {
			return true;
		}
		return false;
	}
}
