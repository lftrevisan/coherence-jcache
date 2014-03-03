package com.adp.portal.cache.coherence;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;

import com.tangosol.net.NamedCache;
import com.tangosol.util.filter.AlwaysFilter;
import com.tangosol.util.processor.ConditionalRemove;

public class CoherenceCache<K, V> implements Cache<K, V> {

	private NamedCache coherenceCache;
	private CacheManager cacheManager;
	
	CoherenceCache(CacheManager cacheManager, NamedCache coherenceCache) {
		super();
		this.cacheManager = cacheManager;
		this.coherenceCache = coherenceCache;
	}

	@Override
	public void clear() {
		coherenceCache.clear();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean containsKey(K key) {
		return coherenceCache.containsKey(key);
	}

	@Override
	public void deregisterCacheEntryListener(
			CacheEntryListenerConfiguration<K, V> arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		return (V)coherenceCache.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> getAll(Set<? extends K> keySet) {
		return coherenceCache.getAll(keySet);
	}

	@Override
	public V getAndPut(K key, V value) {
		//TODO: add optimistic locking approach here
		V currentValue = this.get(key);
		this.put(key, value);
		return currentValue;
	}

	@Override
	public V getAndRemove(K key) {
		//TODO: add optimistic locking approach here
		V currentValue = this.get(key);
		this.remove(key);
		return currentValue;
	}

	@Override
	public V getAndReplace(K key, V value) {
		//TODO: add optimistic locking approach here
		V currentValue = this.get(key);
		this.put(key, value);
		return currentValue;
	}

	@Override
	public CacheManager getCacheManager() {
		return this.cacheManager;
	}

	@Override
	public Configuration<K, V> getConfiguration() {
		return null;
	}

	@Override
	public String getName() {
		return coherenceCache.getCacheName();
	}

	@Override
	public <T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor, Object... arguments)
			throws EntryProcessorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<K, T> invokeAll(Set<? extends K> keySet,
			EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
		return null;
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<javax.cache.Cache.Entry<K, V>> iterator() {
		return coherenceCache.values().iterator();
	}

	@Override
	public void loadAll(Set<? extends K> entrySet, boolean replace,
			CompletionListener completionListener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void put(K key, V value) {
		coherenceCache.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> collection) {
		coherenceCache.putAll(collection);
	}

	@Override
	public boolean putIfAbsent(K key, V value) {
		//TODO: add optimistic locking approach here
		if (!this.containsKey(key)) {
			this.put(key, value);
			return true;
		}
		return false;
	}

	@Override
	public void registerCacheEntryListener(
			CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean remove(K key) {
		return coherenceCache.remove(key) != null;
	}

	@Override
	public boolean remove(K key, V value) {
		//TODO: add optimistic locking approach here
		if (value != null && value.equals(this.get(key))) {
			return this.remove(key);
		}
		return false;
	}

	@Override
	public void removeAll() {
		coherenceCache.clear();
	}

	@Override
	public void removeAll(Set<? extends K> keySet) {
		this.coherenceCache.invokeAll(keySet,
				new ConditionalRemove(AlwaysFilter.INSTANCE));
	}

	@Override
	public boolean replace(K key, V value) {
		//TODO: add optimistic locking approach here
		if (this.containsKey(key)) {
			this.put(key, value);
			return true;
		}
		return false;
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		//TODO: add optimistic locking approach here
		if (oldValue != null) {
			if (oldValue.equals(this.get(key))) {
				this.put(key, newValue);
				return true;
			}
		}
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		return null;
	}

}
