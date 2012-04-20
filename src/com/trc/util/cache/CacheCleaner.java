package com.trc.util.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheCleaner implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(CacheCleaner.class);

  @Scheduled(fixedDelay = 60000)
  public void run() {
    if (CacheManager.cache != null && CacheManager.cache.size() > 0) {
      logger.trace("CacheCleaner running on Thread {}. Current size is {}.", Thread.currentThread().getName(), CacheManager.cache.size());
      for (Map.Entry<String, CachedObject> entry : CacheManager.cache.entrySet()) {
        if (CacheManager.isExpired(entry.getValue()))
          CacheManager.cache.remove(entry.getKey());
      }
      logger.trace("After cleaning, cache size is now {}", CacheManager.cache.size());
    } else {
      logger.trace("CacheCleaner running on Thread {}. No garbage collection necessary.", Thread.currentThread().getName());
    }
  }

}