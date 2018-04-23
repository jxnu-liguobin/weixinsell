package cn.edu.jxnu.controller;

import com.lly835.bestpay.model.PayResponse;

import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.service.OrderService;
import cn.edu.jxnu.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

/**
 * 支付 返回界面
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月18日
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PayService payService;

	// 支付只需要传一个订单id过来，再去数据库查询金额，安全
	@GetMapping("/create")
	public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl,
			Map<String, Object> map) {
		// 1. 查询订单
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}

		// 2. 发起支付
		PayResponse payResponse = payService.create(orderDTO);

		map.put("payResponse", payResponse);
		map.put("returnUrl", returnUrl); // 支付完成,前端回调url

		return new ModelAndView("pay/create", map);
	}

	/**
	 * 接收微信异步通知,判断支付成功
	 * 
	 * @param notifyData
	 */
	@PostMapping("/notify")
	public ModelAndView notify(@RequestBody String notifyData) {
		payService.notify(notifyData);

		// 返回给微信处理结果
		return new ModelAndView("pay/success");
	}
}
