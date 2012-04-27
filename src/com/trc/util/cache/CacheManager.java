package com.trc.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;

public final class CacheManager {
  protected static volatile Map<String, CachedObject> cache = new ConcurrentHashMap<String, CachedObject>();
  public static final Logger logger = LoggerFactory.getLogger(CacheManager.class);
  private static final int MAX_CACHE_SIZE = 1000;
  protected static final long TIME_TO_LIVE_IN_MILLISECOND = 1 * 60 * 1000;

  /**
   * Clear all cache entries
   * 
   * @param user
   */
  public static final void clearCache() {
    cache.clear();
  }

  /**
   * Clear cache entry for the given user
   * 
   * @param user
   */
  public static final void clearCache(final User user) {
    logger.trace("Clearing cache for User {}:{}", user.getUserId(), user.getEmail());
    if (user == null)
      return;
    for (String key : cache.keySet()) {
      if (key.startsWith(Integer.toString(user.getUserId())))
        cache.remove(key);
    }
  }

  public static final void clearCache(final User user, final CacheKey cacheKey) {
    remove(CacheKey.makeKey(user, cacheKey));
  }

  public static final void clearCache(final User user, final CacheKey cacheKey, final String modifier) {
    remove(CacheKey.makeKey(user, cacheKey, modifier));
  }

  public static final Object get(String key) {
    if (key != null && cache.containsKey(key)) {
      CachedObject obj = (CachedObject) cache.get(key);
      if (!isExpired(obj))
        return obj.getObjectToCache();
      else
        remove(key);
    }
    return null;
  }

  public static final Object get(final User user, final CacheKey cacheKey) {
    return get(user, cacheKey, null);
  }

  public static final Object get(final User user, final CacheKey cacheKey, final Object obj) {
    if (obj instanceof Account) {
      return get(user, cacheKey, ((Account) obj).getAccountno());
    } else if (obj instanceof Device) {
      return get(user, cacheKey, ((Device) obj).getId());
    } else if (obj instanceof Integer) {
      return get(user, cacheKey, Integer.toString((Integer) obj));
    } else if (obj instanceof String) {
      return get(user, cacheKey, (String) obj);
    } else {
      return get(user, cacheKey, null);
    }
  }

  public static final Object get(final User user, final CacheKey cacheKey, final String modifier) {
    String newKey = CacheKey.makeKey(user, cacheKey, modifier);
    if (cache.containsKey(newKey)) {
      CachedObject obj = (CachedObject) cache.get(newKey);
      if (obj != null && !isExpired(obj)) {
        logger.debug("Returning entry={} value={}", newKey, obj);
        return obj.getObjectToCache();
      } else {
        remove(newKey);
      }
    }
    return null;
  }

  protected static final boolean isExpired(CachedObject obj) {
    if (!(obj instanceof CachedObject))
      return false;
    else
      return ((System.currentTimeMillis() - obj.getStartTime()) > TIME_TO_LIVE_IN_MILLISECOND ? true : false);
  }

  protected static final boolean isFull() {
    return ((cache.size() > MAX_CACHE_SIZE) ? true : false);
  }

  public static final boolean isCached(User user, CacheKey cacheKey) {
    return isCached(user, cacheKey, null);
  }

  /**
   * Currently unused as containsKey is marginally slower than an actual get.
   * 
   * @param user
   * @param cacheKey
   * @param modifier
   * @return
   */
  public static final boolean isCached(User user, CacheKey cacheKey, String modifier) {
    return cache.containsKey(CacheKey.makeKey(user, cacheKey, modifier));
  }

  protected static final void remove(String key) {
    cache.remove(key);
    logger.trace("Removing entry {} from cache", key);
  }

  public static final void set(String key, Object obj) {
    if (key == null || obj == null)
      return; // do nothing
    if (!isFull())
      cache.put(key, new CachedObject(obj, System.currentTimeMillis()));
  }

  public static final void set(final User user, final CacheKey cacheKey, final Object obj) {
    if (user == null || obj == null)
      return; // do nothing
    if (!isFull()) {
      String newKey = CacheKey.makeKey(user, cacheKey, obj);
      cache.put(newKey, new CachedObject(obj, System.currentTimeMillis()));
      logger.debug("Caching entry={} value={} cacheSize={}", new Object[] { newKey, obj, cache.size() });
    }
  }

  private CacheManager() {
    // Only allows static access to the methods
  }

}
