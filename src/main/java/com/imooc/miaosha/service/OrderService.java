package com.imooc.miaosha.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.imooc.miaosha.dao.OrderDao;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * 判断用户是否已经秒杀过了
	 * @param id
	 * @param goodsId
	 * @return
	 */
	public MiaoshaOrder getOrderByUserIdGoodsId(Long id, long goodsId) {
		return orderDao.getOrderByUserIdGoodsId(id, goodsId);
	}
	/**
	 * 生成秒杀订单
	 * @param user
	 * @param good
	 * @return
	 */
	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo good) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(good.getId());
		orderInfo.setGoodsName(good.getGoodsName());
		orderInfo.setGoodsPrice(good.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId=orderDao.insertOrderInfo(orderInfo);
		
		MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
		miaoshaOrder.setGoodsId(good.getId());
		miaoshaOrder.setOrderId(orderId);
		miaoshaOrder.setUserId(user.getId());
		int count = orderDao.insertMiaoshaOrder(miaoshaOrder);
		
		return orderInfo;
	}
}
