package com.adp.portal.cache.coherence;

import java.net.URI;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;

public class CoherenceCacheManager implements CacheManager {

	private static final String COHERENCE_URL_KEY = "tangosol.coherence.cacheconfig";
	private static final String COHERENCE_CONFIG_FILE = "coherence_config.properties";
	private ConfigurableCacheFactory configurableCacheFactory;
	private CachingProvider provider;

	private Log log = LogFactory.getLog(CoherenceCacheManager.class);

	CoherenceCacheManager(CachingProvider provider) {
		this.provider = provider;
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

	private String getConfigUrl() {
		String configURL = null;
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			config.addConfiguration(new PropertiesConfiguration(
					COHERENCE_CONFIG_FILE));
		} catch (ConfigurationException e) {
			log.warn("Error reading coherence configuration file: "+ COHERENCE_CONFIG_FILE);
		}
		configURL = config.getString(COHERENCE_URL_KEY);
		if (configURL == null) {
			log.error("Missing oracle coherence configuration settings");
			throw new RuntimeException("Missing oracle coherence configuration settings");
		}
		return configURL;

	}

	private NamedCache getCoherenceCache(String cacheName) {
		if (configurableCacheFactory == null) {
			synchronized (this) {
				CacheFactoryBuilder cfb = CacheFactory.getCacheFactoryBuilder();
				configurableCacheFactory = cfb.getConfigurableCacheFactory(
						getConfigUrl(),
						CoherenceCacheManager.class.getClassLoader());
			}
		}
		return configurableCacheFactory.ensureCache(cacheName, this.getClass()
				.getClassLoader());
	}

	@Override
	public <K, V> Cache<K, V> getCache(String cacheName) {
		CoherenceCache<K, V> cache = new CoherenceCache<>(this,
				getCoherenceCache(cacheName));
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
		return this.provider;
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
