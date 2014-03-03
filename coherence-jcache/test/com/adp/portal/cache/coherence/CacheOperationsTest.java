package com.adp.portal.cache.coherence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.junit.Test;

public class CacheOperationsTest {

	private static final String CACHE_NAME = "example";
	private static final String CONFIG_FILE_KEY = "tangosol.coherence.cacheconfig";
	private static final String CONFIG_FILE_VALUE = "coherence.xml";
	private static final String KEY = "key";
	private static final String KEY2 = "key2";

	@Test
	public void cachingProviderTest() {
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cacheManager = cachingProvider.getCacheManager();

		Cache<String, Integer> cache = cacheManager.getCache(CACHE_NAME);

		// cache operations
		Integer value1 = 1;
		cache.put(KEY, value1);
		Integer value2 = cache.get(KEY);
		assertEquals(value1, value2);
		cache.remove(KEY);
		assertNull(cache.get(KEY));
	}

	@Test
	public void directCachingAccessTest() {
		Cache<String, Integer> cache = Caching.getCache(CACHE_NAME,
				String.class, Integer.class);

		Integer value1 = 1;
		cache.put(KEY, value1);
		Integer value2 = cache.get(KEY);
		assertEquals(value1, value2);
		cache.remove(KEY);
		assertNull(cache.get(KEY));
	}
	
	@Test(expected=RuntimeException.class)
	public void noConfigurationProvidedTest() {
		System.setProperty(CONFIG_FILE_KEY, "unexistentFile.xml");
		try {
			Caching.getCache(CACHE_NAME, String.class, Integer.class);
		}
		finally {
			System.clearProperty(CONFIG_FILE_KEY);
		}
	}
	
	@Test
	public void systemPropertyTest() {
		System.setProperty(CONFIG_FILE_KEY, CONFIG_FILE_VALUE);
		Cache <String, Integer> cache = null;
		try {
			cache = Caching.getCache(CACHE_NAME, String.class, Integer.class);
		}
		finally {
			System.clearProperty(CONFIG_FILE_KEY);
		}
		assertNotNull(cache);
	}
	
	@Test
	public void clearTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.put(KEY, "any");
		assertTrue(cache.containsKey(KEY));
		cache.clear();
		assertFalse(cache.containsKey(KEY));
	}
	
	@Test
	public void containsKeyTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		cache.put(KEY, "any");
		assertTrue(cache.containsKey(KEY));
		assertFalse(cache.containsKey(KEY+"2"));
	}
	
	@Test
	public void getAndRemoveTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		cache.put(KEY, value);
		assertEquals(cache.getAndRemove(KEY), value);
		assertFalse(cache.containsKey(KEY));
	}
	
	@Test
	public void getAndReplaceTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		String value2 = "any2";
		cache.put(KEY, value);
		assertEquals(cache.getAndReplace(KEY, value2), value);
		assertEquals(cache.get(KEY), value2);
	}
	
	@Test
	public void getAllTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		cache.put(KEY, value);
		String value2 = "any";
		cache.put(KEY2, value2);
		Map<String, String> entries = cache.getAll(Collections.singleton(KEY));
		assertEquals(entries.size(), 1);
		assertEquals(entries.get(KEY), value);
	}
	
	@Test
	public void getAndPutTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String oldValue = "any";
		cache.put(KEY, oldValue);
		String newValue = "any";
		assertEquals(cache.getAndPut(KEY, newValue), oldValue);
		assertEquals(cache.get(KEY), newValue);
	}
	
	@Test
	public void putAllTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		Map<String, String> entries = new HashMap<String, String>();
		String value = "any";
		entries.put(KEY, value);
		entries.put(KEY2, value);
		cache.putAll(entries);
		assertEquals(cache.get(KEY), value);
		assertEquals(cache.get(KEY2), value);
	}
	
	@Test
	public void putIfAbsentTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		assertTrue(cache.putIfAbsent(KEY, value));
		assertEquals(cache.get(KEY), value);
		assertFalse(cache.putIfAbsent(KEY, value));
	}
	
	@Test
	public void removeTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		cache.put(KEY, value);
		String value2 = "any2";
		assertFalse(cache.remove(KEY, value2));
		assertTrue(cache.containsKey(KEY));
		assertTrue(cache.remove(KEY, value));
		assertFalse(cache.containsKey(KEY));
	}
	
	@Test
	public void removeAllWithFilteringTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		cache.put(KEY, value);
		cache.put(KEY2, value);
		
		cache.removeAll(Collections.singleton(KEY));
		
		assertFalse(cache.containsKey(KEY));
		assertTrue(cache.containsKey(KEY2));
	}

	
	@Test
	public void removeAllTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String value = "any";
		cache.put(KEY, value);
		cache.put(KEY2, value);
		
		cache.removeAll();
		
		assertFalse(cache.containsKey(KEY));
		assertFalse(cache.containsKey(KEY2));
	}
	
	@Test
	public void replaceTest() {
		Cache <String, String> cache = Caching.getCache(CACHE_NAME, String.class, String.class);
		cache.clear();
		String oldValue = "any";
		String newValue = "any2";
		cache.put(KEY, oldValue);
		
		// inexistent key
		assertFalse(cache.replace(KEY2, oldValue));
		
		// existent key
		assertTrue(cache.replace(KEY, newValue));
		assertEquals(cache.get(KEY), newValue);
		
		// inexistent key
		assertFalse(cache.replace(KEY2, oldValue, newValue));
		
		// existent key
		assertTrue(cache.replace(KEY, newValue, oldValue));
		assertEquals(cache.get(KEY), oldValue);
	}
	
}

