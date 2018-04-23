package cn.edu.jxnu.dto;

import lombok.Data;

/**
 * 购物车数据传输对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月17日
 */
@Data
public class CartDTO {

	/** 商品Id. */
	private String productId;

	/** 数量. */
	private Integer productQuantity;

	public CartDTO(String productId, Integer productQuantity) {
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
}
