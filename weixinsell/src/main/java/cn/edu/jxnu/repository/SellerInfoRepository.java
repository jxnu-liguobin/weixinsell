package cn.edu.jxnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.jxnu.dataobject.SellerInfo;

/**
 * 卖家
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
	SellerInfo findByOpenid(String openid);
}
