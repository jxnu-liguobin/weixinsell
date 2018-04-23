package cn.edu.jxnu.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.edu.jxnu.dataobject.OrderDetail;
import cn.edu.jxnu.repository.OrderDetailRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情测试
 * @author 梦境迷离
 * @version V1.0
 * @time.	2018年4月13日
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository repository;

	@Test
	public void saveTest() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDetailId("1234567810");
		orderDetail.setOrderId("11111112");
		orderDetail.setProductIcon("http://xxxx.jpg");
		orderDetail.setProductId("11111112");
		orderDetail.setProductName("皮蛋粥");
		orderDetail.setProductPrice(new BigDecimal(2.2));
		orderDetail.setProductQuantity(3);

		OrderDetail result = repository.save(orderDetail);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByOrderId() throws Exception {
		List<OrderDetail> orderDetailList = repository.findByOrderId("11111111");
		Assert.assertNotEquals(0, orderDetailList.size());
	}

}