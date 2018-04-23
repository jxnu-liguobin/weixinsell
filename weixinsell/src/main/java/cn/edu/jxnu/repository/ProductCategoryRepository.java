package cn.edu.jxnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.edu.jxnu.dataobject.ProductCategory;

import java.util.List;

/**
 * 商品类目
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
