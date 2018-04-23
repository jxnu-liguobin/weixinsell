package cn.edu.jxnu.converter;

import cn.edu.jxnu.dataobject.OrderMaster;
import cn.edu.jxnu.dto.OrderDTO;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 将主订单转化为订单信息传输对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
public class OrderMaster2OrderDTOConverter {

	/** 单个对象转化 . */
	public static OrderDTO convert(OrderMaster orderMaster) {

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}

	/** 列表对象转化 . */
	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		// lambda
		return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
	}
}
