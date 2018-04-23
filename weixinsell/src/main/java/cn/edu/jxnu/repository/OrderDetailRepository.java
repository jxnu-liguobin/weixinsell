package cn.edu.jxnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.jxnu.dataobject.OrderDetail;

import java.util.List;

/**
 * 订单详情
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

	List<OrderDetail> findByOrderId(String orderId);
}
