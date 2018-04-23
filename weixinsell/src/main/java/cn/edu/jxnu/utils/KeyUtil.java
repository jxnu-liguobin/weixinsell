package cn.edu.jxnu.utils;

import java.util.Random;

/**
 * 主键生成工具
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public class KeyUtil {

	/**
	 * 生成唯一的主键 格式: 时间+随机数
	 * 
	 * @return String
	 */
	public static synchronized String genUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(900000) + 100000;

		return System.currentTimeMillis() + String.valueOf(number);
	}
}
