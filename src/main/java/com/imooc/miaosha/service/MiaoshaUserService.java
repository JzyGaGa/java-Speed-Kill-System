package com.imooc.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {
	@Autowired
	private MiaoshaUserDao miaoshaUserDao ;
	
	public MiaoshaUser getByid(long id) {
		return miaoshaUserDao.getById(id);
	}

	public CodeMsg login(LoginVo vo) {
		if(vo==null)
			return CodeMsg.SERVER_ERROR;
		String mobile = vo.getMobile();
		String password = vo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getByid(Long.parseLong(mobile));
		if(user==null) {
			return CodeMsg.MOBILE_NOT_EXITS;
		}
		//验证密码
		String dbPass=user.getPassword();
		String saltDB=user.getSalt();
		String clacPass = MD5Util.formPassToDbPass(dbPass, saltDB);
		if(dbPass.equals(clacPass)) {
			return CodeMsg.PASSWORD_ERROR;
		}
		return CodeMsg.SUCCESS;
	}

}
