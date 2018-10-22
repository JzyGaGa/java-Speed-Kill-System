package com.imooc.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
		if(e instanceof BindException) {
			BindException ex=(BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg= error.getDefaultMessage();
			return Result.error(CodeMsg.Bind_ERROR.fillArgs(msg));
		}else {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
