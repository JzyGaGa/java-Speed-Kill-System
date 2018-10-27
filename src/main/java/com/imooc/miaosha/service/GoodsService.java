package com.imooc.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	@Autowired
	private GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
		
	}

	public GoodsVo getGoodsByGoodsId(long goodsId) {
		return goodsDao. getGoodByGoodsId(goodsId);
	}
	/**
	 * 修改上平信息
	 * @param g
	 */
	public void reduceStock(GoodsVo good) {
		MiaoshaGoods g=new MiaoshaGoods();
		g.setId(good.getId());
		g.setStockCount(good.getGoodsStock());
		goodsDao.reduceStock(g);
	}
}
