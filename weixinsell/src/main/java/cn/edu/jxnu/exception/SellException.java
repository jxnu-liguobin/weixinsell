package cn.edu.jxnu.exception;

import cn.edu.jxnu.enums.ResultEnum;
import lombok.Getter;

/**
 * 异常
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018-4-13
 */
@SuppressWarnings("unused")
@Getter
public class SellException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());

		this.code = resultEnum.getCode();
	}

	public SellException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
