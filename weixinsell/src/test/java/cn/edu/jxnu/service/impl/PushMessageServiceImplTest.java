package cn.edu.jxnu.service.impl;

import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.service.OrderService;
import cn.edu.jxnu.service.impl.PushMessageServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 推送消息服务测试
 * 
 * @author 梦境迷离
 * @version V1.0 
 * @time. 2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

	@Autowired
	private PushMessageServiceImpl pushMessageService;

	@Autowired
	private OrderService orderService;

	@Test
	public void orderStatus() throws Exception {

		OrderDTO orderDTO = orderService.findOne("1499097346204378750");
		pushMessageService.orderStatus(orderDTO);
	}

}