package cn.edu.jxnu.enums;

import lombok.Getter;

/**
 * 订单状态
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
	NEW(0, "新订单"), FINISHED(1, "完结"), CANCEL(2, "已取消");

	private Integer code;

	private String message;

	OrderStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
