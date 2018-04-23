package cn.edu.jxnu.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

import cn.edu.jxnu.dto.OrderDTO;

/**
 * 支付
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface PayService {

	/** 创建支付. */
	PayResponse create(OrderDTO orderDTO);

	/** 异步通知回调 ,验证状态正确，并修改订单 . */
	PayResponse notify(String notifyData);

	/** 退款. */
	RefundResponse refund(OrderDTO orderDTO);
}
