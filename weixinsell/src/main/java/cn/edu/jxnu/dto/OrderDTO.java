package cn.edu.jxnu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.edu.jxnu.dataobject.OrderDetail;
import cn.edu.jxnu.enums.OrderStatusEnum;
import cn.edu.jxnu.enums.PayStatusEnum;
import cn.edu.jxnu.utils.EnumUtil;
import cn.edu.jxnu.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单数据传输对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@Data
// @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//已废弃
// @JsonInclude(JsonInclude.Include.NON_NULL)//使用全局配置替代
public class OrderDTO {

	/** 订单id. */
	private String orderId;

	/** 买家名字. */
	private String buyerName;

	/** 买家手机号. */
	private String buyerPhone;

	/** 买家地址. */
	private String buyerAddress;

	/** 买家微信Openid. */
	private String buyerOpenid;

	/** 订单总金额. */
	private BigDecimal orderAmount;

	/** 订单状态, 默认为0(新下单). */
	private Integer orderStatus;

	/** 支付状态, 默认为0(未支付). */
	private Integer payStatus;

	/** 创建时间,自动转化Date类型. */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	/** 更新时间. */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	List<OrderDetail> orderDetailList;

	/** 忽略json字段. */
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}

	@JsonIgnore
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
}
