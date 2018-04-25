package cn.edu.jxnu.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.jxnu.VO.ResultVO;
import cn.edu.jxnu.config.ProjectUrlConfig;
import cn.edu.jxnu.exception.ResponseBankException;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.exception.SellerAuthorizeException;
import cn.edu.jxnu.utils.ResultVOUtil;

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

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = SellException.class)
	@ResponseBody
	public ResultVO handleSellException(SellException e) {

		return ResultVOUtil.error(e.getCode(), e.getMessage());

	}

	/**
	 * 异常触发，捕获并返回403
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月25日
	 * @version v1.0
	 */
	@ExceptionHandler(value = ResponseBankException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void handleResponseBankException() {

	}
}
