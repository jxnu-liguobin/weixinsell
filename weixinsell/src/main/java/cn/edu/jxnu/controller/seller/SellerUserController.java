package cn.edu.jxnu.controller.seller;

import cn.edu.jxnu.config.ProjectUrlConfig;
import cn.edu.jxnu.constant.CookieConstant;
import cn.edu.jxnu.constant.RedisConstant;
import cn.edu.jxnu.entity.SellerInfo;
import cn.edu.jxnu.enums.ResultEnum;
import cn.edu.jxnu.service.SellerService;
import cn.edu.jxnu.utils.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	/**
	 * 登陆
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 * @param openid
	 * @param response
	 * @param map
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response,
			Map<String, Object> map) {

		// 1. openid去和数据库里的数据匹配
		SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
		if (sellerInfo == null) {
			map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
			// 测试用
			map.put("url", "/weixinsell/seller/order/list");
			return new ModelAndView("common/error");
		}

		// 2. 设置token至redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;
		// String.format(RedisConstant.TOKEN_PREFIX, token) 格式化token
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire,
				TimeUnit.SECONDS);

		// 3. 设置token至cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

		// 跳转的时候最好使用绝对路径
		return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/weixinsell/seller/order/list");

	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		// 1. 从cookie里查询name=token的
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie != null) {
			// 2. 清除redis
			redisTemplate.opsForValue().getOperations()
					.delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

			// 3. 清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		}

		map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
		// 测试用
		map.put("url", "/weixinsell/seller/order/list");
		return new ModelAndView("common/success", map);
	}
}
