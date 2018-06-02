package cn.edu.jxnu.form;

import lombok.Data;

/**
 * 商品类目表单
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年6月2日
 */
@Data
public class CategoryForm {

	private Integer categoryId;

	/** 类目名字. */
	private String categoryName;

	/** 类目编号. */
	private Integer categoryType;
}
