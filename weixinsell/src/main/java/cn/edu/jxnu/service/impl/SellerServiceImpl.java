package cn.edu.jxnu.service.impl;

import cn.edu.jxnu.entity.SellerInfo;
import cn.edu.jxnu.repository.SellerInfoRepository;
import cn.edu.jxnu.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卖家
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerInfoRepository repository;

	@Override
	public SellerInfo findSellerInfoByOpenid(String openid) {
		return repository.findByOpenid(openid);
	}
}
