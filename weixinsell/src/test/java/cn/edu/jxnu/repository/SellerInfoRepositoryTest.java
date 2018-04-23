package cn.edu.jxnu.repository;

import cn.edu.jxnu.entity.SellerInfo;
import cn.edu.jxnu.repository.SellerInfoRepository;
import cn.edu.jxnu.utils.KeyUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 卖家信息测试
 * 
 * @author 梦境迷离
 * @version V1.0 
 * @time. 2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

	@Autowired
	private SellerInfoRepository repository;

	@Test
	public void save() {
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerId(KeyUtil.genUniqueKey());
		sellerInfo.setUsername("admin");
		sellerInfo.setPassword("admin");
		sellerInfo.setOpenid("abc");

		SellerInfo result = repository.save(sellerInfo);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByOpenid() throws Exception {
		SellerInfo result = repository.findByOpenid("abc");
		Assert.assertEquals("abc", result.getOpenid());
	}

}