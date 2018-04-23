package cn.edu.jxnu.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.edu.jxnu.dto.OrderDTO;
import cn.edu.jxnu.service.OrderService;
import cn.edu.jxnu.service.PayService;

/**
 * 支付服务测试
 * @author 梦境迷离
 * @version V1.0
 * @time.	2018年4月13日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1499097366838352541");
        payService.create(orderDTO);
    }

    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1499592887470659070");
        payService.refund(orderDTO);
    }

}