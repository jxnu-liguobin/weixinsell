package cn.edu.jxnu.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具 格式化
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public class JsonUtil {

	public static String toJson(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
}
