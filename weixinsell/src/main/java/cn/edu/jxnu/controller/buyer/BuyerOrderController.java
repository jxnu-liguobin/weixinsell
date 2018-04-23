package cn.edu.jxnu.controller.buyer;

import cn.edu.jxnu.VO.ResultVO;
import cn.edu.jxnu.converter.OrderForm2OrderDTOConverter;
import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.form.OrderForm;
import cn.edu.jxnu.service.BuyerService;
import cn.edu.jxnu.service.OrderService;
import cn.edu.jxnu.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private BuyerService buyerService;

	// 创建订单
	@SuppressWarnings("unchecked")
	@PostMapping("/create")
	public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
		// 这里一个@Valid的参数后必须紧挨着一个BindingResult 参数，否则spring会在校验不通过时直接抛出异常
		// 判断表单验证有错误抛出异常
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确, orderForm={}", orderForm);
			// 将验证错误信息返回到前端
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		// 将订单表单对象转化为订单数据传输对象
		OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【创建订单】购物车不能为空");
			throw new SellException(ResultEnum.CART_EMPTY);
		}
		OrderDTO createResult = orderService.create(orderDTO);
		Map<String, String> map = new HashMap<>();
		map.put("orderId", createResult.getOrderId());

		return ResultVOUtil.success(map);
	}

	// 订单列表
	@SuppressWarnings("unchecked")
	@GetMapping("/list")
	public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		if (StringUtils.isEmpty(openid)) {
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		PageRequest request = new PageRequest(page, size);
		Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

		return ResultVOUtil.success(orderDTOPage.getContent());
	}

	// 订单详情
	@SuppressWarnings("unchecked")
	@GetMapping("/detail")
	public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
		// TODO 不安全,待改进
		OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
		return ResultVOUtil.success(orderDTO);
	}

	// 取消订单
	@SuppressWarnings("rawtypes")
	@PostMapping("/cancel")
	public ResultVO cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
		// TODO 不安全,待改进
		buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success();
	}
}
