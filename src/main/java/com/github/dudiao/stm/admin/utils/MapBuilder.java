package com.github.dudiao.stm.admin.utils;

import java.util.HashMap;

/**
 * @author songyinyin
 * @since 2022/11/19 20:53
 */
public class MapBuilder {

  public static <K, V> HashMap<K, V> of(K key, V value) {
    final HashMap<K, V> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  public static <K, V> HashMap<K, V> of(K key1, V value1, K key2, V value2) {
    final HashMap<K, V> map = new HashMap<>();
    map.put(key1, value1);
    map.put(key2, value2);
    return map;
  }

  public static <K, V> HashMap<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3) {
    final HashMap<K, V> map = new HashMap<>();
    map.put(key1, value1);
    map.put(key2, value2);
    map.put(key3, value3);
    return map;
  }
}
