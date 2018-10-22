# java-Speed-Kill-System
----------
## 1.参数校验  ##

### 1.1对mobile参数的检验 ###
			String passInput=vo.getPassword();
			String mobile=vo.getMobile();
			if(StringUtils.isEmpty(passInput)) {
				return Result.error(CodeMsg.PASSWORD_EMPTY);
			}
			if(mobile.isEmpty()) {
				return Result.error(CodeMsg.MOBILE_EMPTY);
			}
			if(!ValidatorUtil.isMobile(mobile)) {
				return Result.error(CodeMsg.MOBILE_ERROR);
			}
每次都会写好多的判断，很没必要，而且很不简洁，但是这个作者采用注解的形式，使代码优化很棒
####1.1.1 注解ismobile ####
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@Constraint(validatedBy = {IsMobileValidator.class})
	public @interface IsMobile {
		
	boolean required() default true;
	
	String message() default "手机号码错误";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	}
#### 1.1.2编写校验器 ####
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


 #### 1.1.3类的那个属性校验 ####

	public class LoginVo {
	@NotNull
	@IsMobile
	private String mobile;
	@NotNull
	@Length(min=32)
	private String password;
	public String getMobile() {
		return mobile;
	}
 #### 1.1.3哪个类 ####
    package com.imooc.miaosha.controller;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;


@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	private MiaoshaUserService userService;
	
	private static Logger log=LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/to_login")
	public String toLogin() {
		
		return "Login";
	}
	@RequestMapping("/error")
	public String error() {
		
		return "Login";
	}
	@RequestMapping("/do_login")
	@ResponseBody
	public  Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo vo) {
		log.info(vo.toString());
		//参数校验
		//		String passInput=vo.getPassword();
		//		String mobile=vo.getMobile();
		//		if(StringUtils.isEmpty(passInput)) {
		//			return Result.error(CodeMsg.PASSWORD_EMPTY);
		//		}
		//		if(mobile.isEmpty()) {
		//			return Result.error(CodeMsg.MOBILE_EMPTY);
		//		}
		//		if(!ValidatorUtil.isMobile(mobile)) {
		//			return Result.error(CodeMsg.MOBILE_ERROR);
		//		}
		//登陆
		CodeMsg cm = userService.login(response,vo);
		if(cm.getCode()==0) {
			return Result.success(true);
		}else {
			return Result.error(cm);
		}
	}
	}
**这样代码就变的简洁的许多，从中得到了很多启发，也顺便研究了注解**
****
## @CookieValue ##
 @CookieValue的作用：@CookieValue是SpringMvc的注解，专门来获取Cookie中的值
 @CookieValue参数
    

   	/**
	 * Alias for {@link #name}.
	 */
	@AliasFor("name")
	String value() default "";
	
	/**
	 * The name of the cookie to bind to.
	 * @since 4.2
	 */
	**要绑定到的cookie的名称。**
	@AliasFor("value")
	String name() default "";

	/**
	 * Whether the cookie is required.
	 * <p>Defaults to {@code true}, leading to an exception being thrown
	 * if the cookie is missing in the request. Switch this to
	 * {@code false} if you prefer a {@code null} value if the cookie is
	 * not present in the request.
	 * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
	 * sets this flag to {@code false}.
	 */
	boolean required() default true;

	/**
	 * The default value to use as a fallback.
	 * <p>Supplying a default value implicitly sets {@link #required} to
	 * {@code false}.
	 */
	String defaultValue() default ValueConstants.DEFAULT_NONE;
	

