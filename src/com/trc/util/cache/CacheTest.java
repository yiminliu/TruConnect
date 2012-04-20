package com.trc.util.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trc.user.User;
import com.trc.util.cache.CacheManager;
import com.trc.util.cache.CachedObject;

import static org.junit.Assert.*;

public class CacheTest {

  String testKey;
  String testValue;
  String testValue1;
  String badTestKey;
  User user1;

  @Before
  public void init() throws Exception {
    testKey = "testKey";
    testValue = "testString";
    testValue1 = "testString1";
    badTestKey = "badTestKey";
    user1 = new User();
    user1.setUserId(1234);
  }

  @Test
  public void testPut() {
    CacheManager.set(testKey, testValue);
    assertNotNull(CacheManager.get(testKey));
    // CacheManager.remove(testKey);
  }

  @Test
  public void testGet() {
    CacheManager.set(testKey, testValue);
    assertEquals(testValue, CacheManager.get(testKey));
    // CacheManager.remove(testKey);
  }

  @Test
  public void testIsExpired() {
    CachedObject obj = new CachedObject(testValue, System.currentTimeMillis() - 10 * 10000000);
    CacheManager.set(testKey, obj);
    assertEquals(true, CacheManager.isExpired(obj));
    // CacheManager.remove(testKey);
  }

  @Test
  public void testRemove() {
    CacheManager.set(testKey, testValue);
    assertNotNull(CacheManager.get(testKey));
    CacheManager.remove(testKey);
    assertNull(CacheManager.get(testKey));
  }

  @Test
  public void putNullKey() {
    CacheManager.set(null, testValue);
    assertNull(CacheManager.get(null));
  }

  @Test
  public void getNull() {
    // CacheManager.put(testKey, testValue);
    assertEquals(null, CacheManager.get(null));
    // CacheManager.remove(testKey);
  }

  @Test
  public void getWithBadKey() {
    // CacheManager.put(testKey, testValue);
    assertEquals(null, CacheManager.get(badTestKey));
    // CacheManager.remove(testKey);
  }

  @Test
  public void replaceValue() {
    CacheManager.set(testKey, testValue);
    assertEquals(testValue, CacheManager.get(testKey));
    CacheManager.set(testKey, testValue1);
    assertEquals(testValue, CacheManager.get(testKey));

    assertEquals(testValue1, CacheManager.get(testKey));
    // CacheManager.remove(testKey);
  }

  @Test
  public void putUser() {
    CacheManager.set(user1, CacheKey.ADDRESSES, testValue);
    assertNotNull(CacheManager.get(user1, CacheKey.ADDRESSES));
    // CacheManager.clearCache(user1, CacheKey.ADDRESSES);
  }

  @Test
  public void getUser() {
    CacheManager.set(user1, CacheKey.ADDRESSES, testValue);
    assertEquals(testValue, CacheManager.get(user1, CacheKey.ADDRESSES));
    // System.out.println(new CacheManager().toString());
    // CacheManager.clearCache(user1, CacheKey.ADDRESSES);
  }

  @Test
  public void putWithNullKey() {
    CacheManager.clearCache(user1, CacheKey.ADDRESSES);
    CacheManager.set(null, testValue);
    assertNull(CacheManager.get(user1, CacheKey.ADDRESSES));
  }

  @Test
  public void putUserWithNullValue() {
    CacheManager.set(user1, CacheKey.ADDRESSES, null);
    assertNull(null, CacheManager.get(user1, CacheKey.ADDRESSES));
  }

  @Test
  public void getUserWithBadKey() {
    assertNull(CacheManager.get(new User(), CacheKey.ADDRESSES));

  }

  @Test
  public void replaceUserValue() {
    CacheManager.set(user1, CacheKey.ADDRESSES, testValue);
    assertEquals(testValue, CacheManager.get(user1, CacheKey.ADDRESSES));
    CacheManager.set(user1, CacheKey.ADDRESSES, testValue1);
    assertEquals(testValue1, CacheManager.get(user1, CacheKey.ADDRESSES));
    CacheManager.clearCache(user1, CacheKey.ADDRESSES);
  }

  @Test
  public void testClearUser() {
    CacheManager.clearCache();
    CacheManager.set(user1, CacheKey.ADDRESSES, testValue);
    CacheManager.set(user1, CacheKey.DEVICES, "device");
    assertEquals(2, CacheManager.cache.size());
    CacheManager.clearCache(user1);
    assertEquals(0, CacheManager.cache.size());
  }

  @After
  public void cleanup() throws Exception {
    CacheManager.clearCache();
  }
}
