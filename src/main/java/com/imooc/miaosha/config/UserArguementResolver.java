package com.imooc.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;
@Service
public class UserArguementResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	private MiaoshaUserService userService;
	/**
	 * 如果遇到MiaoshaUser.class进行下一步
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz==MiaoshaUser.class ;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		
			String paramToken=request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
			String cookieToken=getCookieValue(request,MiaoshaUserService.COOKI_NAME_TOKEN);
			if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
				return "login";
			}
			String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
			return userService.getByToken(response,token);
	}

	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookiName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
