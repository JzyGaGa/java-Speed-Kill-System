package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 从jedis中取出值
	 * @param key  键值对
	 * @param clazz  要转换成类的类型
	 * @return
	 */
	public <T> T get(KeyPrefix prefix,String key, Class<T> clazz) {
		Jedis jedis =null;
		try {
			 jedis = jedisPool.getResource();
			 //生成真正的key
			 String realKey=prefix.getPrefix()+key;
			 String str = jedis.get(realKey);
			 T t=stringToBean(str,clazz);
			 return t;
		}finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 向redis中写入值
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean set(KeyPrefix prefix,String key, T value) {
		Jedis jedis =null;
		try {
			 jedis = jedisPool.getResource();
			 String str=beanToString(value);
			 if(str==null||str.length()<=0)
				 return false;
			 //生成真正的key
			 String realKey=prefix.getPrefix()+key;
			 int seconds=prefix.expireSeconds();
			 if(seconds<=0) {
				 //永久存活
				 jedis.set(realKey, str);
			 }else {
				 jedis.setex(realKey, seconds, str);
			 }
			  jedis.set(realKey,str);
			 return true;
		}finally {
			returnToPool(jedis);
		}
	}
	/**
	 * jedis key+1
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> Long incr(KeyPrefix prefix,String key) {
		Jedis jedis=null;
		try {
			jedis=jedisPool.getResource();
			//生成真正的key
			String realKey= prefix.getPrefix()+key;
			return jedis.incr(realKey);
		}finally {
			returnToPool(jedis);
		}
		
	}
	
	/**
	 * jedis key-1
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> Long decr(KeyPrefix prefix,String key) {
		Jedis jedis=null;
		try {
			jedis=jedisPool.getResource();
			//生成真正的key
			String realKey= prefix.getPrefix()+key;
			//对它的值
			return jedis.decr(realKey);
		}finally {
			returnToPool(jedis);
		}
		
	}
	private <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz= value.getClass();
		if(clazz==int.class||clazz==Integer.class) {
			return ""+value;
		}else if(clazz==long.class||clazz==Long.class) {
			return ""+value;
		}else if(clazz==String.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
 	}

	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String str,Class<T> clazz) {
		if(str==null||str.length()<=0||clazz==null)
			return null;
		if(clazz==int.class||clazz==Integer.class)
			return (T)Integer.valueOf(str);
		//parseInt返回的是基本类型，valueOf返回的是对象
		else if(clazz==long.class||clazz==Long.class) {
			return (T) Long.valueOf(str);
		}else if(clazz==String.class) {
			return (T) str;
		}
		return JSON.toJavaObject(JSON.parseObject(str), clazz);
	}

	private void returnToPool(Jedis jedis) {
		if(jedis!=null) {
			jedis.close();
		}
		
	}
}
