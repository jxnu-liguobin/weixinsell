package cn.edu.jxnu.service;

import cn.edu.jxnu.dto.OrderDTO;

/**
 * 买家
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface BuyerService {

	/** 查询一个订单. */
	OrderDTO findOrderOne(String openid, String orderId);

	/** 取消订单. */
	OrderDTO cancelOrder(String openid, String orderId);
}
