package cn.edu.jxnu.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.edu.jxnu.entity.ProductInfo;
import cn.edu.jxnu.repository.ProductInfoRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品信息测试
 * 
 * @author 梦境迷离
 * @version V1.0 
 * @time. 2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

	@Autowired
	private ProductInfoRepository repository;

	@Test
	public void saveTest() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123456");
		productInfo.setProductName("皮蛋粥");
		productInfo.setProductPrice(new BigDecimal(3.2));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("很好喝的粥");
		productInfo.setProductIcon("http://xxxxx.jpg");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(2);

		ProductInfo result = repository.save(productInfo);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByProductStatus() throws Exception {

		List<ProductInfo> productInfoList = repository.findByProductStatus(0);
		Assert.assertNotEquals(0, productInfoList.size());
	}

}