# java-Speed-Kill-System
----------
## 参数校验  ##
 比如：
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
每次都会写好多的判断，很没必要



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
	

