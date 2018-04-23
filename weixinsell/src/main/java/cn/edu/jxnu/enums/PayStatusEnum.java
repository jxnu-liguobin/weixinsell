package cn.edu.jxnu.enums;

import lombok.Getter;

/**
 * 支付状态
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

	WAIT(0, "等待支付"), SUCCESS(1, "支付成功");

	private Integer code;

	private String message;

	PayStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
