package cn.edu.jxnu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.jxnu.dataobject.OrderMaster;

/**
 * 订单
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
