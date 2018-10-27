package com.imooc.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.MiaoShaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {
	// 定义cookie的名字
	public final static String COOKI_NAME_TOKEN = "token";
	@Autowired
	private MiaoshaUserDao miaoshaUserDao;
	@Autowired
	private RedisService redisService;
	/**
	 * 查找MiaoshaUser by id.
	 * @param id
	 * @return
	 */
	public MiaoshaUser getByid(long id) {
		return miaoshaUserDao.getById(id);
	}
	/**
	 * 登陆验证
	 * @param response
	 * @param vo
	 * @return
	 */
	public CodeMsg login(HttpServletResponse response, LoginVo vo) {
		if (vo == null)
			return CodeMsg.SERVER_ERROR;
		String mobile = vo.getMobile();
		String password = vo.getPassword();
		// 判断手机号是否存在
		MiaoshaUser user = getByid(Long.parseLong(mobile));
		if (user == null) {
			return CodeMsg.MOBILE_NOT_EXITS;
		}
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String clacPass = MD5Util.formPassToDbPass(dbPass, saltDB);
		if (dbPass.equals(clacPass)) {
			return CodeMsg.PASSWORD_ERROR;
		}
		// 加入cookie
		// 登陆成功之后，生成redis缓存的一级id
		String token = UUIDUtil.uuid();
		addCookie(response,user,token);
		return CodeMsg.SUCCESS;
	}
	/**
	 * 向cookie添加redis的一级id,和更新
	 * @param response
	 * @param user
	 */
	private void addCookie(HttpServletResponse response,MiaoshaUser user,String token) {
		// 生成redis缓存的二级id作为建写入redis
		redisService.set(MiaoShaUserKey.Token, token, user);
		// 生成redis缓存的一级id写入cookie
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		// 设置生存时间
		cookie.setMaxAge(MiaoShaUserKey.Token.expireSeconds());
		//可在同一应用服务器内共享方法：设置cookie.setPath("/"); 
		cookie.setPath("/");
		// 设置cookie
		response.addCookie(cookie);
	}
	/**
	 * 根据redis的一级id查找值
	 * 
	 * @param token
	 * @return
	 */
	public MiaoshaUser getByToken(HttpServletResponse response,String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		//redis内部一级id生成二级id的值
		MiaoshaUser miaoshaUser = redisService.get(MiaoShaUserKey.Token, token, MiaoshaUser.class);
		//假定session过期时间是半小时，1.00访问时，1.30过期，1.10访问时1.40过期。
		// 更新cookie
		if(miaoshaUser!=null) {
			addCookie(response,miaoshaUser,token);
		}
		//更新完毕
		return miaoshaUser;
	}
	/**
	 * 更新库存生成订单
	 * @param user
	 * 
	 * @param good
	 * @return
	 */
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo good) {
		// TODO Auto-generated method stub
		return null;
	}
}
