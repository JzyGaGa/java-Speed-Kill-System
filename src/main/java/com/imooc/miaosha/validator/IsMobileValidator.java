package com.imooc.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.util.ValidatorUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{
	
	//判断是否要验证
	private boolean required =false;
	
	public void initialize(IsMobile constraintAnnotation) {
		//获取俩面required的值
		required=constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		//必须判断
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			//不是必须判断哎，我看他是不是空
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
