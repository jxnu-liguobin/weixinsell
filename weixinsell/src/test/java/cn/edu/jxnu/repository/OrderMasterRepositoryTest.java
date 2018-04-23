package cn.edu.jxnu.repository;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.edu.jxnu.entity.OrderMaster;
import cn.edu.jxnu.repository.OrderMasterRepository;

/**
 * 订单测试
 * 
 * @author 梦境迷离
 * @version V1.0 
 * @time. 2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

	@Autowired
	private OrderMasterRepository repository;

	private final String OPENID = "110110";

	@Test
	public void saveTest() {
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId("1234567");
		orderMaster.setBuyerName("师兄");
		orderMaster.setBuyerPhone("123456789123");
		orderMaster.setBuyerAddress("幕课网");
		orderMaster.setBuyerOpenid(OPENID);
		orderMaster.setOrderAmount(new BigDecimal(2.5));

		OrderMaster result = repository.save(orderMaster);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByBuyerOpenid() throws Exception {
		PageRequest request = new PageRequest(1, 3);

		Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);

		Assert.assertNotEquals(0, result.getTotalElements());
	}

}