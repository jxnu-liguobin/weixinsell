package cn.edu.jxnu.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.edu.jxnu.dataobject.SellerInfo;

/**
 * 卖家服务测试
 * 
 * @author 梦境迷离
 * @version V1.0 
 * @time. 2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

	private static final String openid = "abc";

	@Autowired
	private SellerServiceImpl sellerService;

	@Test
	public void findSellerInfoByOpenid() throws Exception {
		SellerInfo result = sellerService.findSellerInfoByOpenid(openid);
		Assert.assertEquals(openid, result.getOpenid());
	}

}