package com.imooc.miaosha.controller;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;


@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	private MiaoshaUserService userService;
	
	private static Logger log=LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/to_login")
	public String toLogin() {
		
		return "Login";
	}
	@RequestMapping("/error")
	public String error() {
		
		return "Login";
	}
	@RequestMapping("/do_login")
	@ResponseBody
	public  Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo vo) {
		log.info(vo.toString());
		//参数校验
//		String passInput=vo.getPassword();
//		String mobile=vo.getMobile();
//		if(StringUtils.isEmpty(passInput)) {
//			return Result.error(CodeMsg.PASSWORD_EMPTY);
//		}
//		if(mobile.isEmpty()) {
//			return Result.error(CodeMsg.MOBILE_EMPTY);
//		}
//		if(!ValidatorUtil.isMobile(mobile)) {
//			return Result.error(CodeMsg.MOBILE_ERROR);
//		}
		//登陆
		CodeMsg cm = userService.login(response,vo);
		if(cm.getCode()==0) {
			return Result.success(true);
		}else {
			return Result.error(cm);
		}
	}
}
