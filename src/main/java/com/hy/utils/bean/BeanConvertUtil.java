package com.hy.utils.bean;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BeanConvertUtil {

    /**
     * 驼峰对象转换为下划线对象
     * @param source 驼峰对象
     * @param target 下划线对象
     * @return
     */
    public static void humpObj2UlObj(Object source, Object target) {
        Map<String,Object> map1 = obj2Map(source);
        Map<String,Object> map2 = new HashMap<>();
        for(String key : map1.keySet()){
            map2.put(hump2Ul(key),map1.get(key));
        }
        Object obj = map2Obj(map2, target.getClass());
        BeanUtils.copyProperties(obj,target);
    }

    /**
     * 下划线对象转换为驼峰对象
     * @param source 下划线对象
     * @param target 驼峰对象
     * @return
     */
    public static void ulObj2HumpObj(Object source, Object target) {
        Map<String,Object> map1 = obj2Map(source);
        Map<String,Object> map2 = new HashMap<>();
        for(String key : map1.keySet()){
            map2.put(ul2Hump(key),map1.get(key));
        }
        Object obj = map2Obj(map2, target.getClass());
        BeanUtils.copyProperties(obj,target);
    }

    /**
     * 下划线对象转换为驼峰对象
     * @param source 下划线对象
     * @param target 驼峰对象
     * @return
     */
    public static void ulMap2HumpObj(Map<String,Object> map1, Object target) {
        Map<String,Object> map2 = new HashMap<>();
        for(String key : map1.keySet()){
            map2.put(ul2Hump(key),map1.get(key));
        }
        Object obj = map2Obj(map2, target.getClass());
        BeanUtils.copyProperties(obj,target);
    }


    /**
     * 实体对象转成Map
     * @param obj
     * @return
     */
    public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        @SuppressWarnings("rawtypes")
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Obj(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    int mod = field.getModifiers();
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(obj, map.get(field.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * [字符串]驼峰转下划线
     * @param str
     * @return
     */
    public static String hump2Ul(String str){
        StringBuilder sb=new StringBuilder(str);
        int temp=0;
        if (!str.contains("_")) {
            for(int i=0;i<str.length();i++){
                if(Character.isUpperCase(str.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * [字符串]下划线转驼峰
     * @param str
     * @return
     */
    public static String ul2Hump(String str){
        StringBuilder result=new StringBuilder();
        String a[]=str.split("_");
        for(String s:a){
            if (!str.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

}
