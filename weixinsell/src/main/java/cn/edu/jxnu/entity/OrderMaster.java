package cn.edu.jxnu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import cn.edu.jxnu.enums.OrderStatusEnum;
import cn.edu.jxnu.enums.PayStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Entity
@Data
@DynamicUpdate /** 动态更新【但是插入相同数据不会更新】. */
public class OrderMaster {

	/** 订单id. */
	@Id
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
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	/** 支付状态, 默认为0(未支付). */
	private Integer payStatus = PayStatusEnum.WAIT.getCode();

	/** 创建时间. */
	private Date createTime;

	/** 更新时间. */
	private Date updateTime;

}
