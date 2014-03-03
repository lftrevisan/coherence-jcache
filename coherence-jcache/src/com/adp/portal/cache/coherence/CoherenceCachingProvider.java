package com.adp.portal.cache.coherence;

import java.net.URI;
import java.util.Properties;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;

public class CoherenceCachingProvider implements CachingProvider {

	private final CacheManager cacheManager;
	
	public CoherenceCachingProvider() {
		this.cacheManager = new CoherenceCacheManager(this);
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ClassLoader arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(URI arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	@Override
	public CacheManager getCacheManager(URI arg0, ClassLoader arg1) {
		return getCacheManager();
	}

	@Override
	public CacheManager getCacheManager(URI arg0, ClassLoader arg1,
			Properties arg2) {
		return getCacheManager();
	}

	@Override
	public ClassLoader getDefaultClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getDefaultProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getDefaultURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSupported(OptionalFeature arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
