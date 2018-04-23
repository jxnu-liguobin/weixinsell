package cn.edu.jxnu.service.impl;

import cn.edu.jxnu.dataobject.ProductCategory;
import cn.edu.jxnu.repository.ProductCategoryRepository;
import cn.edu.jxnu.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ProductCategoryRepository repository;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		return repository.findOne(categoryId);
	}

	@Override
	public List<ProductCategory> findAll() {
		return repository.findAll();
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return repository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return repository.save(productCategory);
	}
}
