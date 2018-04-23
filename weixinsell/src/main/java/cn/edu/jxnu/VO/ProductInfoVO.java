package cn.edu.jxnu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情视图对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Data
public class ProductInfoVO {

	/** 指定返回的json对象的属性. */
	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPrice;

	@JsonProperty("description")
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;
}
