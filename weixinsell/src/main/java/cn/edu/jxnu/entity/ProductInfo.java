package cn.edu.jxnu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.edu.jxnu.enums.ProductStatusEnum;
import cn.edu.jxnu.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

	@Id
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

	/** 状态, 0(正常),1(下架)，默认是上架状态. */
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	/** 类目编号. */
	private Integer categoryType;

	/** 创建时间. */
	private Date createTime;

	/** 更新时间. */
	private Date updateTime;

	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	}
}
