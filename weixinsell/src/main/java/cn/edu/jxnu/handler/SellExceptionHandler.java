package cn.edu.jxnu.handler;

import cn.edu.jxnu.config.ProjectUrlConfig;
import cn.edu.jxnu.exception.SellerAuthorizeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@ControllerAdvice
public class SellExceptionHandler {

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	// 拦截登录异常
	// http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView(
				"redirect:".concat(projectUrlConfig.getWechatOpenAuthorize())
				.concat("/weixinsell/wechat/qrAuthorize")
				.concat("?returnUrl=")
				.concat(projectUrlConfig.getSell())
				.concat("/weixinsell/seller/login"));
	}
}
