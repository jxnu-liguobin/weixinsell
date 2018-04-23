package cn.edu.jxnu.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信开放平台配置
 * 
 * 个人开发者无法测试这部分代码
 * 
 * 只有企业有权限使用的接口
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月24日
 */
@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig accountConfig;

	@Bean
	public WxMpService wxOpenService() {
		WxMpService wxOpenService = new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
		return wxOpenService;
	}

	@Bean
	public WxMpConfigStorage wxOpenConfigStorage() {
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
		wxMpInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
		return wxMpInMemoryConfigStorage;
	}
}
