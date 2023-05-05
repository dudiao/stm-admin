package com.github.dudiao.stm.admin.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author songyinyin
 * @since 2022/10/4 13:23
 */
public class CacheHolder {

  /**
   * key: 实体类名
   * value: key 缓存名，value 缓存值
   */
  private static final Map<Class<?>, Map<String, Object>> entityCacheMap = new ConcurrentHashMap<>();

  private static final Object NULL_VALUE = new Object();

  /**
   * 将某个实体中的数据缓存到 JVM 中
   *
   * @param entityClass 实体类
   * @param key         key
   * @param supplier    缓存中不存在时，调用
   */
  public static <T> T getEntityCache(Class<?> entityClass, String key, Supplier<?> supplier) {
    Map<String, Object> map = entityCacheMap.get(entityClass);
    if (map == null) {
      Object newValue = supplier.get();
      map = new HashMap<>();
      map.put(key, newValue);
      entityCacheMap.put(entityClass, map);
      return (T) newValue;
    }
    Object value = map.get(key);
    if (value == null) {
      Object newValue = supplier.get();
      if (newValue == null) {
        map.put(key, NULL_VALUE);
        return null;
      }
      return (T) newValue;
    }
    if (NULL_VALUE.equals(value)) {
      return null;
    }
    return (T) value;
  }

  /**
   * 清除某个实体的缓存
   */
  public static void clearEntity(Class<?> entityClass) {
    entityCacheMap.remove(entityClass);
  }

  /**
   * 清除所有实体的缓存
   */
  public static void clearAllEntity() {
    entityCacheMap.clear();
  }

}
