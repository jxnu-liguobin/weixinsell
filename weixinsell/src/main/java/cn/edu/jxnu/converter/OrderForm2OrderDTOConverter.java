package cn.edu.jxnu.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.entity.OrderDetail;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单表单对象转化为订单数据传输对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

	public static OrderDTO convert(OrderForm orderForm) {
		Gson gson = new Gson();
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());

		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			// 解析Items中的订单详情列表
			orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
			}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误, string={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}
}
