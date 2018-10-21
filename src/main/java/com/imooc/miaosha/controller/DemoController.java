package com.imooc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;

@RequestMapping("/demo")
@Controller
public class DemoController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean> setRedis() {
		User user = new User();
		user.setId(1);
		user.setName("贾志远");
		Boolean res= redisService.set(UserKey.getById, ""+1,user);
		return Result.success(res);
	}
	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> getRedis() {
		User user = redisService.get(UserKey.getById,""+1, User.class);//UserKey.getById
		System.out.println(UserKey.getById);//UserKey:id1
		return Result.success(user);
	}
	@RequestMapping("db/get")
	@ResponseBody
	public Result<User> dbGet() {
		User user = userService.getById(1);
		return Result.success(user);
	}

	@RequestMapping("hello")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("success")
	@ResponseBody
	public Result<String> helloSuccess() {
		return Result.success("data.success");
	}

	@RequestMapping("error")
	@ResponseBody
	public Result<String> helloerror() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	@RequestMapping("/hellohtml")
	public String hello(Model model) {
		model.addAttribute("name", "jia");
		return "hello";
	}
}
