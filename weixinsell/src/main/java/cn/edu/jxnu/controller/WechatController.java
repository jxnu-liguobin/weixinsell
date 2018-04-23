package cn.edu.jxnu.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.jxnu.config.ProjectUrlConfig;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * 微信授权，使用github的微信sdk
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@Controller // 需要重定向的时候不能使用RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WxMpService wxOpenService;

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	/**
	 * 
	 * 第一步：请求CODE 此方法实现请求授权
	 * 
	 * @time 下午6:17:37
	 * @version V1.0
	 * @param returnUrl
	 * @return 重定向 string
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
		// 1. 配置
		// 2. 调用方法
		String url = projectUrlConfig.getWechatMpAuthorize() + "/weixinsell/wechat/userInfo";
		// OAUTH2_SCOPE_BASE 默认直接授权
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE,
				URLEncoder.encode(returnUrl));// 重定向到回调接口地址 redirectUrl，必须编码url
		log.info("授权：{}", redirectUrl);
		return "redirect:" + redirectUrl;
	}

	/**
	 * 第二步：通过code获取access_token 用户允许授权后，将会重定向到redirect_uri的网址上，并且带上code和state参数
	 * authorize重定向到这个方法获取用户信息
	 * 
	 * @time 下午6:17:59
	 * @version V1.0
	 * @param code
	 * @param returnUrl
	 * @return 重定向 string
	 */
	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		try {
			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			// 继续抛出
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
		}
		// 拿到openid
		String openId = wxMpOAuth2AccessToken.getOpenId();

		log.info("获得openid:{}", openId);
		// 这个接口前端和后端的开发文档规定的,视情况而定
		return "redirect:" + returnUrl + "?openid=" + openId;
	}

	/**
	 * 开放平台验证请求
	 * 
	 * 二维码验证授权入口
	 * 
	 * 与authorize方法基本相同
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 * @param returnUrl
	 * @return string 重定向地址
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/qrAuthorize")
	public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
		String url = projectUrlConfig.getWechatOpenAuthorize() + "/weixinsell/wechat/qrUserInfo";
		String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,
				URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}

	/**
	 * 二维码验证授权
	 * 
	 * 获得openid
	 * 
	 * 与userInfo方法基本相同
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 * @param code
	 * @param returnUrl
	 * @return String 重定向地址
	 */
	@GetMapping("/qrUserInfo")
	public String qrUserInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		try {
			wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
		}
		log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
		String openId = wxMpOAuth2AccessToken.getOpenId();

		return "redirect:" + returnUrl + "?openid=" + openId;
	}
}
