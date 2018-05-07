package cn.edu.jxnu.controller.buyer;

import cn.edu.jxnu.VO.ProductInfoVO;
import cn.edu.jxnu.VO.ProductVO;
import cn.edu.jxnu.VO.ResultVO;
import cn.edu.jxnu.entity.ProductCategory;
import cn.edu.jxnu.entity.ProductInfo;
import cn.edu.jxnu.service.CategoryService;
import cn.edu.jxnu.service.ProductService;
import cn.edu.jxnu.utils.ResultVOUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * 买家商品
 * 
 * @time 2018年4月16日
 * @version V1.0
 * @author 梦境迷离.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@SuppressWarnings("rawtypes")
	@GetMapping("/list") // get方法的list
	@Cacheable(cacheNames = "product", key = "list") // 缓存,product:list
	public ResultVO list() {
		// 查询所有的上架商品
		List<ProductInfo> productInfoList = productService.findUpAll();

		// 查询类目(这种方法不好)
		// List<Integer> categoryTypeList = new ArrayList<>();
		// 传统方法
		// for (ProductInfo productInfo : productInfoList) {
		// categoryTypeList.add(productInfo.getCategoryType());
		// }
		// 精简方法(java8，一次性查询商品类目,map映射中仅仅提取商品的类目)
		List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		// 根据商品类目id,查询商品类目对象
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
		// 数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		// 先遍历商品类目
		for (ProductCategory productCategory : productCategoryList) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryType(productCategory.getCategoryType());
			productVO.setCategoryName(productCategory.getCategoryName());
			// 类目下还有商品详情
			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				// 判断相等
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					// 将productInfo属性值拷贝到productInfoVO中
					BeanUtils.copyProperties(productInfo, productInfoVO);
					// 添加商品详情视图对象
					productInfoVOList.add(productInfoVO);
				}
			}
			// 添加商品详情对象
			productVO.setProductInfoVOList(productInfoVOList);
			// 添加商品视图对象
			productVOList.add(productVO);
		}

		return ResultVOUtil.success(productVOList);
	}
}
