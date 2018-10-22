package com.imooc.miaosha.util;

import java.util.UUID;
/**
 * 创建的cookie的token
 * @author 59842
 *
 */
public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
