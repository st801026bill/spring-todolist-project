package com.bill.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class EhCacheConfig extends CachingConfigurerSupport {

	/*
	 * 1.	查詢圖片的key為NewsFrom字串+NewsSeqNo字串
	 * 2.	cache中的物件最多保留小時數由sql查詢資料庫而來 (MaxHours : 24)
	 * 3. 最多的物件數量上限由sql查詢資料庫而來(MaxSize : 100)
	 * 4.	磁碟儲存使用預設的暫存路徑
	 * 5.	cacheNames定義為EmployeeNewsPicCache
	 */
	public net.sf.ehcache.CacheManager todolistEhCacheManager() {

		long maxHours = 24L;
		long maxSize = 100L;

		CacheConfiguration todoListQueryCache = new CacheConfiguration();
		todoListQueryCache.setName("TodoListCache");
		todoListQueryCache.setTimeToLiveSeconds(maxHours*60*60);
		todoListQueryCache.setMaxEntriesLocalHeap(maxSize);
		todoListQueryCache.setMemoryStoreEvictionPolicy("LRU");

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(todoListQueryCache);
		
		return net.sf.ehcache.CacheManager.newInstance(config);
	}
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(todolistEhCacheManager());
	}
}
