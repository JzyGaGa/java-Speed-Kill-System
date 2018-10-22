# java-Speed-Kill-System
# 参数校验 #
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