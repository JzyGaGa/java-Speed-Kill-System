package com.imooc.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User getById(Integer id){
		return userDao.getById(id);
	}
}
