package cn.edu.jxnu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 微信授权,不会再用这个手动的
 * 
 * 手工获取openid,然后获取access_token
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@Deprecated
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

	@GetMapping("/auth")
	public void auth(@RequestParam("code") String code) {
		log.info("进入auth方法。。。");
		log.info("code={}", code);
		// url来自微信文档,appid是开发者id,secret是开发者密钥，只能用测试号
		// 通过接口：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx11959a5df106ac3a&redirect_uri=http%3A%2F%2Fweixinsell.nat300.top%2Fweixinsell%2Fweixin%2Fauth&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect
		// 得到openid "openid":"oHhC3wh2kJ1UcFsPgO_iDefjcNRc"
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx11959a5df106ac3a&secret=47609bae088c91892f1f45f3675a30f8&code="
				+ code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		log.info("response={}", response);
	}
}
