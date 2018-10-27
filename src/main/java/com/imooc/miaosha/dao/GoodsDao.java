package com.imooc.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {
	@Select("SELECT g.*,mg.miaosha_price,mg.end_date,mg.start_date,mg.stock_count FROM goods g LEFT JOIN miaosha_goods mg ON mg.goods_id=g.id")
	public List<GoodsVo> listGoodsVo();
	
	@Select("SELECT g.*,mg.miaosha_price,mg.end_date,mg.start_date,mg.stock_count FROM goods g LEFT JOIN miaosha_goods mg ON mg.goods_id=g.id WHERE mg.goods_id= #{goodsId}")
	public GoodsVo getGoodByGoodsId(@Param("goodsId")long goodsId);
	@Update("UPDATE miaosha_goods set stock_count=stock_count-1 where goods_id=#{id}")
	public int  reduceStock(MiaoshaGoods g);
}
