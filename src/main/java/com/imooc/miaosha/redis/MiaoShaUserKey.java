package com.imooc.miaosha.redis;

public class MiaoShaUserKey extends BasePrefix {
	/**
	 * 设置redis过期时间
	 */
	private final static int TOKEN_EXPIRE=3600*24*2;
	private  MiaoShaUserKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}
	//生成MiaoShaUserKey
	public static MiaoShaUserKey Token =new MiaoShaUserKey(TOKEN_EXPIRE,"tk");
	
	
	public static void main(String[] args) {
		System.out.println(MiaoShaUserKey.Token.getPrefix());
	}
}
