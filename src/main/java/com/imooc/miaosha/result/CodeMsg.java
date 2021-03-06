package com.imooc.miaosha.result;

public class CodeMsg {
	private int code;
	private String msg;
	
	//通用异常
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg Bind_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500211, "手机不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500211, "手机号码错误");
	public static CodeMsg MOBILE_NOT_EXITS = new CodeMsg(500211, "手机号码不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500212, "密码错误");
	//商品模块 5003XX
	
	//订单模块 5004XX
	
	//秒杀模块 5005XX
	public static CodeMsg MIAOSHA_FALIURE = new CodeMsg(500501, "库存不足");
	public static CodeMsg REPEAT_ERROR = new CodeMsg(500502, "你已經秒杀过了");
	
	
	public CodeMsg fillArgs(Object... args) {
		int code=this.code;
		String message=String.format(this.msg, args);
		return new CodeMsg(code, message);
	}
	
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}
