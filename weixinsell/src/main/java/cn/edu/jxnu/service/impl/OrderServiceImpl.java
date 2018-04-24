package cn.edu.jxnu.service.impl;

import cn.edu.jxnu.converter.OrderMaster2OrderDTOConverter;
import cn.edu.jxnu.dto.CartDTO;
import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.entity.OrderDetail;
import cn.edu.jxnu.entity.OrderMaster;
import cn.edu.jxnu.entity.ProductInfo;
import cn.edu.jxnu.enums.OrderStatusEnum;
import cn.edu.jxnu.enums.PayStatusEnum;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.repository.OrderDetailRepository;
import cn.edu.jxnu.repository.OrderMasterRepository;
import cn.edu.jxnu.service.*;
import cn.edu.jxnu.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderMasterRepository orderMasterRepository;

	@Autowired
	private PayService payService;

	@Autowired
	private PushMessageService pushMessageService;

	@Autowired
	private WebSocket webSocket;

	/**
	 * 创建订单
	 *
	 * @time 下午1:36:58
	 * @version V1.0
	 * @param orderDTO
	 * @return 订单数据传输对象
	 */
	@Override
	@Transactional // 开启事务
	public OrderDTO create(OrderDTO orderDTO) {

		// 生成订单id
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		// List<CartDTO> cartDTOList = new ArrayList<>(); //这样麻烦，直接用lambda的map
		// 1. 查询商品(数量, 价格)
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
			if (productInfo == null) {
				// 商品信息不存在，抛出异常(商品不存在)
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			// 2. 计算订单总价
			orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);
			// 订单详情入库
			orderDetail.setDetailId(KeyUtil.genUniqueKey());// 订单详情的id
			orderDetail.setOrderId(orderId);// 主订单的id
			// 先设置属性值后从商品信息(productInfo)拷贝属性值到orderDetail,避免null覆盖值，但是两个对象的属性名需要一致
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailRepository.save(orderDetail);
			// CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),
			// orderDetail.getProductQuantity());
			// cartDTOList.add(cartDTO);
		}
		// 3. 写入订单数据库(orderMaster,orderDetail)
		OrderMaster orderMaster = new OrderMaster();
		// 将订单传输对象转化为主订单对象
		orderDTO.setOrderId(orderId);// 设置开头产生的订单id
		BeanUtils.copyProperties(orderDTO, orderMaster);
		// 设置金额、订单状态、支付状态
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);
		// 4. 扣库存
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				// 使用lambda的map映射，生成一个购物车数据传输对象的list(实际前台只是传来了商品的id和数量)
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);
		// 发送websocket消息，用户下单触发
		webSocket.sendMessage(orderDTO.getOrderId());

		return orderDTO;
	}

	/**
	 * 根据订单id,查找并返回订单数据传输对象
	 *
	 * @time 下午1:37:14
	 * @version V1.0
	 * @param orderId
	 * @return 订单数据传输对象
	 */
	@Override
	public OrderDTO findOne(String orderId) {

		OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
		if (orderMaster == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}

		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}

		// 根据订单id,查询主订单信息，转化为订单数据传输对象并返回
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);

		return orderDTO;
	}

	/**
	 * 根据买家openid和分页参数，查询订单数据传输对象并返回list集合
	 *
	 * @time 下午1:37:47
	 * 
	 * @version V1.0
	 * @param buyerOpenid
	 * @param pageable
	 * @return list集合
	 */
	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

		// 将主订单转化为订单数据传输对象
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}

	/**
	 * 取消订单
	 *
	 * @time 下午1:39:04
	 * @version V1.0
	 * @param orderDTO
	 * @return orderDTO订单数据传输对象
	 */
	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();

		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		// 返回库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
		productService.increaseStock(cartDTOList);

		// 如果已支付, 需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			payService.refund(orderDTO);
		}

		return orderDTO;
	}

	/**
	 * 完成订单
	 *
	 * @time 下午1:39:39
	 * @version V1.0
	 * @param orderDTO
	 * @return OrderDTO
	 */
	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		// 推送微信模版消息
		pushMessageService.orderStatus(orderDTO);

		return orderDTO;
	}

	/**
	 * 支付订单
	 *
	 * @time 下午1:39:49
	 * @version V1.0
	 * @param orderDTO
	 * @return OrderDTO
	 */
	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		// 修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}

	/**
	 * 分页查询所有并返回订单数据传输对象 卖家后端管理系统使用
	 * 
	 * @time 下午1:39:57
	 * @version V1.0
	 * @param pageable
	 * @return list集合
	 */
	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}
}
