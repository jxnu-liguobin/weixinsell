package cn.edu.jxnu.controller.seller;

import cn.edu.jxnu.entity.ProductCategory;
import cn.edu.jxnu.exception.SellException;
import cn.edu.jxnu.form.CategoryForm;
import cn.edu.jxnu.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 类目列表 无分页
	 * 
	 * @param map
	 * @return ModelAndView
	 */
	@GetMapping("/list")
	public ModelAndView list(Map<String, Object> map) {
		List<ProductCategory> categoryList = categoryService.findAll();
		map.put("categoryList", categoryList);
		return new ModelAndView("category/list", map);
	}

	/**
	 * 展示
	 * 
	 * 打开新增/修改界面，并显示待修改数据
	 * 
	 * @param categoryId
	 * @param map
	 * @return ModelAndView
	 */
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Map<String, Object> map) {
		if (categoryId != null) {
			ProductCategory productCategory = categoryService.findOne(categoryId);
			map.put("category", productCategory);
		}

		return new ModelAndView("category/index", map);
	}

	/**
	 * 新增/更新
	 * 
	 * @param form
	 * @param bindingResult
	 * @param map
	 * @return ModelAndView
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult, Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/weixinsell/seller/category/index");
			return new ModelAndView("common/error", map);
		}

		ProductCategory productCategory = new ProductCategory();
		try {
			/** id不为空，修改时先查询，再拷贝替换，重新写入数据库 */
			if (form.getCategoryId() != null) {
				productCategory = categoryService.findOne(form.getCategoryId());
			}
			BeanUtils.copyProperties(form, productCategory);
			categoryService.save(productCategory);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/weixinsell/seller/category/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/weixinsell/seller/category/list");
		return new ModelAndView("common/success", map);
	}
}
