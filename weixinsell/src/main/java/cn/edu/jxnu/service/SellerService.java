package cn.edu.jxnu.service;

import cn.edu.jxnu.entity.SellerInfo;

/**
 * 卖家
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface SellerService {

	/** 通过openid查询卖家端信息 . */
	SellerInfo findSellerInfoByOpenid(String openid);
}
