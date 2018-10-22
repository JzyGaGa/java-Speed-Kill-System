package com.imooc.miaosha.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ValidatorUtil {
	
	private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");
	/**
	 * 验证手机号吗是不是正确
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		if(str==null)
			return false;
		Matcher matcher = mobile_pattern.matcher(str);
		return matcher.matches();
	}
	public static void main(String[] args) {
		System.out.println(isMobile("12312312312"));
		
	}
}
