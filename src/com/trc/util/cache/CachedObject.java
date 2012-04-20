package com.trc.util.cache;

public class CachedObject {

  private Object objectToCache;
  private long startTime;

  CachedObject(Object objectToCache, long startTime) {
    this.objectToCache = objectToCache;
    this.startTime = startTime;
  }

  public Object getObjectToCache() {
    return objectToCache;
  }

  public void setObjectToCache(Object objectToCache) {
    this.objectToCache = objectToCache;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

}
