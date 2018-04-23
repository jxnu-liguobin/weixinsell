package cn.edu.jxnu.utils;

/**
 * 金额比较工具
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public class MathUtil {

	private static final Double MONEY_RANGE = 0.01;

	/**
	 * 比较2个金额是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Boolean equals(Double d1, Double d2) {
		Double result = Math.abs(d1 - d2);
		if (result < MONEY_RANGE) {
			return true;
		} else {
			return false;
		}
	}
}
