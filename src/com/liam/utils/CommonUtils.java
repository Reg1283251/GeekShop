package com.liam.utils;

import java.util.UUID;

public class CommonUtils {
	
	/**
	 * @return 返回随机生成的UUID
	 */
	public static String getUUID() {
		
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
		
	}

}
