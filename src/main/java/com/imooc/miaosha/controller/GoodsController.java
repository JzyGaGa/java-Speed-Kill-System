package com.imooc.miaosha.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.vo.GoodsVo;

@RequestMapping("/goods")
@Controller
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	
	//  /to_detail{goodsId}
	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model,MiaoshaUser user,
			@PathVariable("goodsId") long goodsId) {
		model.addAttribute("user",user);
		GoodsVo goods=goodsService.getGoodsByGoodsId(goodsId);
		model.addAttribute("goods", goods);
		long startTime = goods.getStartDate().getTime();
		long endTime = goods.getEndDate().getTime();
		long now=System.currentTimeMillis();
		//秒杀状态code
		int miaoshaStatus=0;
		//秒杀剩余的时间
		int remainSeconds=0; 
		if(now<startTime) {//秒杀未开始
			//0代表秒杀未开始
			miaoshaStatus=0;
			remainSeconds=(int) ((startTime-now)/1000);
		}else if(now>endTime){//秒杀已结束
			//2
			miaoshaStatus=2;
			remainSeconds=-1;
		}else {//秒杀进行中
			miaoshaStatus=1;
			remainSeconds=(int) ((endTime-now)/1000);
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}
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
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		//
		model.addAttribute("goodsList",goodsList);
		model.addAttribute("user",user);
		return "goodslist";
	}
	@RequestMapping("/error")
	public String error() {
		
		return "Login";
	}
}
