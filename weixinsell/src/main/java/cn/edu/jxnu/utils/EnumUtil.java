package cn.edu.jxnu.utils;

import cn.edu.jxnu.enums.CodeEnum;

/**
 * 枚举工具类
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public class EnumUtil {

	public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
		for (T each : enumClass.getEnumConstants()) {
			if (code.equals(each.getCode())) {
				return each;
			}
		}
		return null;
	}
}
