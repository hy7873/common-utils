package com.hy.utils.option;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: wanghai
 * @Date:2020/1/4 18:00
 * @Copyright:reach-life
 * @Description:
 */
public class OptionalUtils {

    /**
     * 空值判断
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object value) {
        if (value instanceof Integer) {
            return value == null || (Integer) value == 0;
        } else if (value instanceof Long) {
            return value == null || (Long) value == 0;
        } else if (value instanceof String) {
            return value == null || ((String) value).trim().length() == 0;
        } else if (value instanceof List) {
            return value == null || ((List) value).size() == 0;
        }
        return value == null;
    }

    /**
     * 获取分组键
     *
     * @param value
     * @return
     */
    public static String getGroupkey(Object... value) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]).append("#");
        }
        return sb.toString();
    }


    public static String obj2String(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    public static <T> List<T> listToSetList(List<T> list) {
        Set<T> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        List<T> ret = new ArrayList<>();
        for (T t : set) {
            ret.add(t);
        }
        return ret;
    }

    public static <T> List<T> setToList(Set<T> set) {
        List<T> list = new ArrayList<>();
        for (T t : set) {
            list.add(t);
        }
        return list;
    }


    public static boolean isNegative(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() < 0;
        } else if (obj instanceof Long) {
            return ((Long) obj).longValue() < 0;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).compareTo(BigDecimal.ZERO) < 0;
        }
        return false;
    }

    public static boolean isNegativeOrZero(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() <= 0;
        } else if (obj instanceof Long) {
            return ((Long) obj).longValue() <= 0;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).compareTo(BigDecimal.ZERO) <= 0;
        }
        return false;
    }


    public static boolean isZero(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == 0;
        } else if (obj instanceof Long) {
            return ((Long) obj).longValue() == 0;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).compareTo(BigDecimal.ZERO) == 0;
        }
        return false;
    }

    public static boolean isCollectionNotEmpty(@Nullable Collection<?> collection) {
        if (collection != null && collection.size() > 0)
            return true;
        return false;
    }

}
