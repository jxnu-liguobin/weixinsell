package cn.edu.jxnu.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品修改，新增表单
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@Data
public class ProductForm {

	private String productId;

	/** 名字. */
	private String productName;

	/** 单价. */
	private BigDecimal productPrice;

	/** 库存. */
	private Integer productStock;

	/** 描述. */
	private String productDescription;

	/** 小图. */
	private String productIcon;

	/** 类目编号. */
	private Integer categoryType;
}
