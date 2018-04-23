package cn.edu.jxnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.jxnu.dataobject.ProductInfo;

import java.util.List;

/**
 * 商品
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

	List<ProductInfo> findByProductStatus(Integer productStatus);
}
