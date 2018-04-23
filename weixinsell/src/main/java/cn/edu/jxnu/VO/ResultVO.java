package cn.edu.jxnu.VO;

import lombok.Data;

/**
 * http请求返回的最外层json对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月16日
 */
@Data
public class ResultVO<T> {

	/** 错误码. */
	private Integer code;

	/** 提示信息. */
	private String msg;

	/** 具体内容. */
	private T data;// 这个是通用的返回，需要使用泛型
}
