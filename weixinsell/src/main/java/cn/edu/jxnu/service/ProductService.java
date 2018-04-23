package cn.edu.jxnu.service;

import cn.edu.jxnu.dto.CartDTO;
import cn.edu.jxnu.entity.ProductInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface ProductService {

	/** 查询一个商品信息. */
	ProductInfo findOne(String productId);

	/** 查询所有在架商品列表. */
	List<ProductInfo> findUpAll();

	/** 查询所有商品列表. */
	Page<ProductInfo> findAll(Pageable pageable);

	/** 插入单个商品信息. */
	ProductInfo save(ProductInfo productInfo);

	/** 增加库存. */
	void increaseStock(List<CartDTO> cartDTOList);

	/** 减库存. */
	void decreaseStock(List<CartDTO> cartDTOList);

	/** 上架商品. */
	ProductInfo onSale(String productId);

	/** 下架商品. */
	ProductInfo offSale(String productId);
}
