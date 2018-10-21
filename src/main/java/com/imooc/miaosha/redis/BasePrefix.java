package com.imooc.miaosha.redis;
/**
 * 模板模式
 * @author 59842
 *
 */
public abstract class BasePrefix implements KeyPrefix {
	
	private int expireSeconds;  //过期时间
	private String prefix;
	
	public BasePrefix( String prefix) {//0代表永不过期
		this(0,prefix);
	}
	public BasePrefix(int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}

	public int expireSeconds() {
		return expireSeconds;
	}

	public String getPrefix() {
		String name = getClass().getSimpleName();
		return name+":"+prefix;
	}

}
