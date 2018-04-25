package cn.edu.jxnu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocketConfig
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月24日
 */
@Component
public class WebSocketConfig {

	// @Bean 不注释掉单元测试会保错
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
