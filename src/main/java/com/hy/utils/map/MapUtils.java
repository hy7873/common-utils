package com.hy.utils.map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Auther: Michael Michael_evan2030@aliyun.com
 * @Date: 2019-11-05 10:39
 * @Description: Map工具类
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 获取第一个value
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> V getFirstValue(Map<K,V> map) {
        V val = null;
        for (Entry<K,V> entry : map.entrySet()) {
            val = entry.getValue();
            if (val != null) {
                break;
            }
        }
        return val;
    }

    /**
     * 获取第一个key
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> K getFirstKey(Map<K,V> map) {
        K key = null;
        for (Entry<K,V> entry : map.entrySet()) {
            key = entry.getKey();
            if (key != null) {
                break;
            }
        }
        return key;
    }


    public static Map<String,Object> sortMapByKey(Map<String,Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String,Object> sortedMap = new TreeMap(new MapKeyComparator());
        sortedMap.putAll(map);
        return sortedMap;
    }

    public static LinkedHashMap<String,Object> toLinkedHashMap(Map<String,Object> map) {
        LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
        map.forEach((key,value) -> {
            linkedHashMap.put(key,value);
        });
        return linkedHashMap;
    }

    public static Map<String,String> sortMapByKeyV2(Map<String,String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String,String> sortedMap = new TreeMap(new MapKeyComparator());
        sortedMap.putAll(map);
        return sortedMap;
    }


    static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }


    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;
    }


    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return;

    }

    /**
     * json转map
     * @param json
     * @return
     */
    public static Map<String,Object> json2Map(String json) {
        Map<String,Object> map = JSONObject.parseObject(json,Map.class);
        return map;
    }

    /**
     * json转listMap
     * @param json
     * @return
     */
    public static List<Map<String,Object>> json2ListMap(String json) {
        List<Map<String,Object>> listMap = JSONArray.parseObject(json,List.class);
        return listMap;
    }
}
