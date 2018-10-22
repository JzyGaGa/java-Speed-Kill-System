package com.imooc.miaosha.controller;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;

@RequestMapping("/goods")
@Controller
public class GoodsController {
	
	@Autowired
	private MiaoshaUserService userService;
	
	@RequestMapping("/to_list")
	public String toList(Model model,MiaoshaUser user
//			//从请求中cookie  MiaoshaUserService.COOKI_NAME_TOKEN的名字，取出值
//			@CookieValue(value=MiaoshaUserService.COOKI_NAME_TOKEN,required=false) String cookieToken,
//			//某些手机app并不会放在cookie里面,会放在参数中
//			@RequestParam(value=MiaoshaUserService.COOKI_NAME_TOKEN,required=false) String paramToken
			){
//			if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
//				return "login";
//			}
//			//获得了存储在redis的一级id
//			String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//			MiaoshaUser user=userService.getByToken(response,token);
//			//model和response为什么不用返回
			model.addAttribute("user",user);
		return "goodslist";
	}
	@RequestMapping("/error")
	public String error() {
		
		return "Login";
	}
}
