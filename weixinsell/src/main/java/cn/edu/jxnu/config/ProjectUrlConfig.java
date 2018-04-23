package cn.edu.jxnu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信平台url前缀配置
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

	/**
	 * 微信公众平台授权url
	 */
	public String wechatMpAuthorize;

	/**
	 * 微信开放平台授权url
	 */
	public String wechatOpenAuthorize;

	/**
	 * 点餐系统
	 */
	public String sell;
}
