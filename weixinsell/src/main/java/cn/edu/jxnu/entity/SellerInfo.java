package cn.edu.jxnu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息
 * 
 * @author 梦境迷离.
 * @version V1.0
 * @time 2018年4月13日
 */
@Data
@Entity
public class SellerInfo {

	@Id
	private String sellerId;
	/** 卖家用户名. */
	private String username;

	/** 卖家密码. */
	private String password;

	/** 卖家开放平台openid. */
	private String openid;
}
