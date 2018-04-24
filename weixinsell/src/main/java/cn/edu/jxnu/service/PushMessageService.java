package cn.edu.jxnu.service;

import cn.edu.jxnu.dto.OrderDTO;

/**
 * 推送消息
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月24日
 */
public interface PushMessageService {

	/** 订单状态变更消息. */
	void orderStatus(OrderDTO orderDTO);
}
