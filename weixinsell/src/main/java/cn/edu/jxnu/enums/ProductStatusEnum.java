package cn.edu.jxnu.enums;

import lombok.Getter;

/**
 * 商品状态
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
	UP(0, "在架"), DOWN(1, "下架");

	private Integer code;

	private String message;

	ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
