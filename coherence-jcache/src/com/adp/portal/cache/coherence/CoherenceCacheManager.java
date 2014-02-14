package com.adp.portal.cache.coherence;

import java.net.URI;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;

public class CoherenceCacheManager implements CacheManager {

	private static final String COHERENCE_CLIENT_CONFIG = "coherence.xml";
	private final ConfigurableCacheFactory configurableCacheFactory;
	
	public CoherenceCacheManager() {
		CacheFactoryBuilder cfb = CacheFactory.getCacheFactoryBuilder();
		configurableCacheFactory = cfb.getConfigurableCacheFactory(
				COHERENCE_CLIENT_CONFIG, CoherenceCacheManager.class.getClassLoader());
	}

	@Override
	public void close() {
		
	}

	@Override
	public <K, V> Cache<K, V> createCache(String cacheName,
			Configuration<K, V> configuration) throws IllegalArgumentException {
		return getCache(cacheName);
	}

	@Override
	public void destroyCache(String cacheName) {
		NamedCache coherenceCache = getCoherenceCache(cacheName);
		coherenceCache.destroy();
	}

	@Override
	public void enableManagement(String arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableStatistics(String arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	private NamedCache getCoherenceCache(String cacheName) {
		return configurableCacheFactory.ensureCache(cacheName,
				this.getClass().getClassLoader());		
	}
	
	@Override
	public <K, V> Cache<K, V> getCache(String cacheName) {
		CoherenceCache<K, V> cache = new CoherenceCache<>(getCoherenceCache(cacheName));
		return cache;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String cacheName, Class<K> arg1,
			Class<V> arg2) {
		return getCache(cacheName);
	}

	@Override
	public Iterable<String> getCacheNames() {
		return null;
	}

	@Override
	public CachingProvider getCachingProvider() {
		return new CoherenceCachingProvider();
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
