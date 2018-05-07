package cn.edu.jxnu.controller.seller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.jxnu.entity.ProductCategory;
import cn.edu.jxnu.entity.ProductInfo;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.form.ProductForm;
import cn.edu.jxnu.service.CategoryService;
import cn.edu.jxnu.service.ProductService;
import cn.edu.jxnu.utils.KeyUtil;

/**
 * 卖家端商品
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 分页查询
	 * 
	 * 列表
	 * 
	 * @param page
	 * @param size
	 * @param map
	 * @return ModelAndView
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		PageRequest request = new PageRequest(page - 1, size);
		Page<ProductInfo> productInfoPage = productService.findAll(request);
		map.put("productInfoPage", productInfoPage);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("product/list", map);
	}

	/**
	 * 商品上架
	 * 
	 * @param productId
	 * @param map
	 * @return ModelAndView
	 */
	@RequestMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {
		try {
			productService.onSale(productId);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/weixinsell/seller/product/list");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/weixinsell/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	/**
	 * 商品下架
	 * 
	 * @param productId
	 * @param map
	 * @return ModelAndView
	 */
	@RequestMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {
		try {
			productService.offSale(productId);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/weixinsell/seller/product/list");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/weixinsell/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	/**
	 * 如果productId是空，则新增，如果不是空，则查询出商品并显示
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 * @param productId
	 * @param map
	 * @return ModelAndView
	 */
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
			Map<String, Object> map) {
		if (!StringUtils.isEmpty(productId)) {
			ProductInfo productInfo = productService.findOne(productId);
			map.put("productInfo", productInfo);
		}

		// 查询所有的类目
		List<ProductCategory> categoryList = categoryService.findAll();
		map.put("categoryList", categoryList);

		return new ModelAndView("product/index", map);
	}

	/**
	 * 新增/更新
	 * 
	 * 分布式项目,仅传入url,因为图片只能存到同一个地方,可以搭建专门的图片服务器或者使用cdn
	 * 
	 * @param form
	 * @param bindingResult
	 * @param map
	 * @return ModelAndView
	 */
	@PostMapping("/save")
	// @CachePut(cacheNames="product",key="#form.getProductId()")//更新
	@CacheEvict(cacheNames = "product", key = "#form.getProductId()") // 驱除缓存
	public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/weixinsell/seller/product/index");
			return new ModelAndView("common/error", map);
		}

		ProductInfo productInfo = new ProductInfo();
		try {
			/** 如果productId不为空, 说明是修改，从数据库查询 . */
			if (!StringUtils.isEmpty(form.getProductId())) {
				productInfo = productService.findOne(form.getProductId());
			} else {
				/** id为空则是新增,生成一个id . */
				form.setProductId(KeyUtil.genUniqueKey());
			}
			/** 注意先查询，再拷贝，保存 . */
			BeanUtils.copyProperties(form, productInfo);
			productService.save(productInfo);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/weixinsell/seller/product/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/weixinsell/seller/product/list");
		return new ModelAndView("common/success", map);
	}
}
