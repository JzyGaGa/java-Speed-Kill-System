package com.imooc.miaosha.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController {
	
	@Autowired
	private MiaoshaService miaoshaService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	
	//  /to_detail{goodsId}
	@RequestMapping("/do_miaosha")
	public String detail(Model model,MiaoshaUser user,
			@RequestParam("goodsId")long goodsId) {
		if(user==null)
			return "login";
		//判断库存
		GoodsVo good=goodsService.getGoodsByGoodsId(goodsId);
		Integer stockCount = good.getStockCount();
		if(stockCount<1) {
			model.addAttribute("errmsg", CodeMsg.MIAOSHA_FALIURE.getMsg());
			return "miaosha_fail";
		}
		//判断是否秒杀过了
		MiaoshaOrder miaoshaOrder = orderService.getOrderByUserIdGoodsId(user.getId(),goodsId);
		if(miaoshaOrder!=null) {
			model.addAttribute("errmsg",CodeMsg.REPEAT_ERROR);
			return "miaosha_fail";
		}
		//减库存 下订单 写入秒杀订单
		OrderInfo orderInfo =miaoshaService.miaosha(user,good);
		model.addAttribute("orderInfo",orderInfo);
		model.addAttribute("goods",good);
		return "order_detail";
	}
	
}
