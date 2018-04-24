package cn.edu.jxnu.aspect;

import cn.edu.jxnu.constant.CookieConstant;
import cn.edu.jxnu.constant.RedisConstant;
import cn.edu.jxnu.exception.SellerAuthorizeException;
import cn.edu.jxnu.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * AOP校验用户登录
 * 
 * @author 梦境迷离.
 * @time 2018年4月24日
 * @version v1.0
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 定义切入点
	 * 
	 * 不拦截登入登出
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 */
	@Pointcut("execution(public * cn.edu.jxnu.controller.seller.Seller*.*(..))"
			+ "&& !execution(public * cn.edu.jxnu.controller.seller.SellerUserController.*(..))")
	public void verify() {
	}

	/**
	 * 拦截之前验证redis中是否存在token或者 cookie
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月24日
	 * @version v1.0
	 */
	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 查询cookie
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie == null) {
			log.warn("【登录校验】Cookie中查不到token");
			throw new SellerAuthorizeException();
		}

		// 去redis里查询
		String tokenValue = redisTemplate.opsForValue()
				.get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		if (StringUtils.isEmpty(tokenValue)) {
			log.warn("【登录校验】Redis中查不到token");
			throw new SellerAuthorizeException();
		}
	}
}
