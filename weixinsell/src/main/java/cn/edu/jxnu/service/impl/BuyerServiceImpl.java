package cn.edu.jxnu.service.impl;

import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.service.BuyerService;
import cn.edu.jxnu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Service
@Slf4j /** 可以直接使用log属性打印日志 . */
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderService orderService;

	/**
	 * 查询订单
	 *
	 * @time 下午3:21:01
	 * @version V1.0
	 * @param openid
	 * @param orderId
	 * @return OrderDTO
	 */
	@Override
	public OrderDTO findOrderOne(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
	}

	/**
	 * 取消订单
	 *
	 * @time 下午3:20:50
	 * @version V1.0
	 * @param openid
	 * @param orderId
	 * @return OrderDTO
	 */
	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null) {
			log.error("【取消订单】查不到该订单, orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancel(orderDTO);
	}

	/**
	 * 检查订单归属人
	 *
	 * @time 下午3:20:27
	 * @version V1.0
	 * @param openid
	 * @param orderId
	 * @return 订单
	 */
	private OrderDTO checkOrderOwner(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		// 查询订单是这个人的
		if (orderDTO == null) {
			return null;
		}
		// 判断是否是自己的订单
		if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
			log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDTO;
	}
}
