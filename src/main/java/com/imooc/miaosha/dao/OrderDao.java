package com.imooc.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;

@Mapper
public interface OrderDao {
	//秒杀order里面插入一个数据
	@Select("SELECT * from miaosha_order where user_id=#{userId} AND goods_id=#{goodsId}")
	public MiaoshaOrder getOrderByUserIdGoodsId(@Param("userId")Long id, @Param("goodsId")long goodsId);
	//order_info
	@Insert("INSERT INTO order_info (user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date,pay_date"
			+ ")VALUES(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate},#{payDate})")
	@SelectKey(keyColumn="id",keyProperty="id",resultType=long.class,before=false,statement="select last_insert_id()")
	public long insertOrderInfo(OrderInfo orderInfo);
	//MiaoshaOrder
	@Insert("INSERT INTO miaosha_order(user_id,order_id,goods_id)VALUES(#{userId},#{orderId},#{goodsId})")
	public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
	
	
}
