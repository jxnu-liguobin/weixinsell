package cn.edu.jxnu.service;

import java.util.List;

import cn.edu.jxnu.entity.ProductCategory;

/**
 * 类目
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface CategoryService {

	/** 根据类目id，查询类目. */
	ProductCategory findOne(Integer categoryId);

	/** 查询所有商品类目. */
	List<ProductCategory> findAll();

	/** 根据类目id批量查询类目. */
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	/** 插入一条商品类目. */
	ProductCategory save(ProductCategory productCategory);
}
