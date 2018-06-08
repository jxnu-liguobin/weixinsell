package cn.edu.jxnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 对特价商品的秒杀接口进行加锁 key=商品id,value=当前时间+超时时间
 * 
 * @author 梦境迷离.
 * @time 2018年4月25日
 * @version v1.0
 */

@Slf4j
@Component
public class RedisLock {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 实现redis分布式锁
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月25日
	 * @version v1.0
	 * @param key
	 * @param value
	 *            当前时间+超时时间
	 * @return boolean
	 */
	public boolean lock(String key, String value) {

		/** 如果键不存在则新增,存在则不改变已经有的值 . */
		if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
			// 如果设置成功
			return true;
		}

		/**
		 * 
		 * redis键值已经存在的情况
		 * 
		 * 乐观锁，类似CAS操作，这样使得没有获取锁的用户都不能成功操作，高并发下，可能由很多用户出现错误
		 * 
		 * 不加这步会因为业务抛出异常造成死锁
		 */
		String currentValue = redisTemplate.opsForValue().get(key); // 获取当前值
		/** 判断当前新值是否小于当前系统的时间戳. */
		if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
			/** 获取上一个锁【旧值】的时间，getAndSet类似i++ ,先获取，再设置. */
			String oldValue = redisTemplate.opsForValue().getAndSet(key, value); // 返回一个字符串，也就是键的旧值。
			/**
			 * 如果键不存在，则返回null。如果键存在，得到的oldValue不是空，使用旧值与currentValue比较，不相同，则说明已经被其他线程修改过.
			 */
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				/** 说明没有被其他线程修改，加锁成功. */
				return true;
			}
		}
		/** 已经超时，直接返回false,加锁失败. */
		return false;
	}

	/**
	 * 解锁
	 * 
	 * @author 梦境迷离.
	 * @time 2018年4月25日
	 * @version v1.0
	 * @param key
	 * @param value
	 */
	public void unLook(String key, String value) {
		try {
			String currentValue = redisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				redisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("redis分布式锁，解锁失败");
		}
	}
}
