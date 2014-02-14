package com.adp.portal.cache.coherence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.junit.Test;

public class SimpleTest {

	private static final String CACHE_NAME = "example";

	@Test
	public void test() {
		// resolve a cache manager
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cacheManager = cachingProvider.getCacheManager();

		Cache<String, Integer> cache = cacheManager.getCache(CACHE_NAME);

		// cache operations
		String key = "key";
		Integer value1 = 1;
		cache.put(key, value1);
		Integer value2 = cache.get(key);
		assertEquals(value1, value2);
		cache.remove(key);
		assertNull(cache.get(key));
	}
	
	@Test
	public void test2() {
		// resolve a cache manager
		Cache<String, Integer> cache = Caching.getCache(CACHE_NAME, String.class, Integer.class);
		
		// cache operations
		String key = "key";
		Integer value1 = 1;
		cache.put(key, value1);
		Integer value2 = cache.get(key);
		assertEquals(value1, value2);
		cache.remove(key);
		assertNull(cache.get(key));
	}

}
