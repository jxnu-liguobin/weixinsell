package cn.edu.jxnu.dataobject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * 订单详情
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Entity
@Data /** 自动生产get set toString，需要lombok支持. */
public class OrderDetail {

	@Id
	private String detailId;

	/** 订单id. */
	private String orderId;

	/** 商品id. */
	private String productId;

	/** 商品名称. */
	private String productName;

	/** 商品单价. */
	private BigDecimal productPrice;

	/** 商品数量. */
	private Integer productQuantity;

	/** 商品小图(图片url). */
	private String productIcon;
}
