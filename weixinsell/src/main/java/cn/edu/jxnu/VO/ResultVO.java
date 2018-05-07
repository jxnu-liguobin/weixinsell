package cn.edu.jxnu.VO;

import java.io.Serializable;

import lombok.Data;

/**
 * http请求返回的最外层json对象
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月16日
 */
@Data
public class ResultVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677857050959012953L;

	/** 错误码. */
	private Integer code;

	/** 提示信息. */
	private String msg;

	/** 具体内容. */
	private T data;// 这个是通用的返回，需要使用泛型
}
