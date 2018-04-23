package cn.edu.jxnu.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信公众平台配置
 * 
 * @author 梦境迷离
 * @version V1.0
 * @time 2018年4月13日
 */
@Component
public class WechatMpConfig {

	@Autowired
	private WechatAccountConfig accountConfig;

	/**
	 * 微信公众号服务层 bean注册
	 *
	 * @time 下午6:08:13
	 * @version V1.0
	 * @return WxMpService
	 */
	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	/**
	 * 微信公众号配置 bean注册
	 *
	 * @time 下午6:08:41
	 * @version V1.0
	 * @return WxMpConfigStorage
	 */
	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		// 设置开发者的id和密钥
		wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
		wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
		return wxMpConfigStorage;
	}
}
